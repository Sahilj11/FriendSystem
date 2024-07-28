package com.example.credit.connection.service;

import com.example.credit.connection.dto.FriendListDto;
import com.example.credit.connection.dto.FriendReqPendingDto;
import com.example.credit.entities.Friend_request;
import com.example.credit.entities.User_friend;
import com.example.credit.repo.FriendReqRepo;
import com.example.credit.repo.UserFriendRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendRequestService {

    private final UserFriendRepo userFriendRepo;
    private final FriendReqRepo friendReqRepo;

    /**
     * Sending friend request using two ids.
     * First check is done if there is already a friend request exists for the given ids in friend_request table.
     *
     * @param uid2 friend request been sent to this id.
     * @param uid1 logged user sending request.
     */
    // TODO: understand what is it doing.
    public ResponseEntity<String> sendFriendReq(int uid2, int uid1) {
        try {
            log.warn("User with id {} sending request to User with id {}", uid1, uid2);
            Friend_request friend_request = new Friend_request();
            friend_request.setRequestor("UID1");
            if (uid1 < uid2) {
                if (friendReqRepo.existsByUid1(uid1)) {
                    log.warn("User with id {} is already friends with User with id {}", uid1, uid2);
                    return new ResponseEntity<>("You are already friends", HttpStatus.BAD_REQUEST);
                }
                friend_request.setUid1(uid1);
                friend_request.setUid2(uid2);
                friend_request.setRequestor("UID1");
            } else {
                if (friendReqRepo.existsByUid2(uid1)) {
                    log.warn("User with id {} is already friends with User with id {}", uid1, uid2);
                    return new ResponseEntity<>("You are already friends", HttpStatus.BAD_REQUEST);
                }
                friend_request.setUid1(uid2);
                friend_request.setUid2(uid1);
                friend_request.setRequestor("UID2");
            }
            friendReqRepo.save(friend_request);
            return new ResponseEntity<>("Friend request sent.", HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Issue occurred while sending friend request from userId {} to userID {}. {}", uid1, uid2, e.getMessage());
            return new ResponseEntity<>("Issue occurred while sending request try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Logged user accept friend_request.
     * First check is performed if action is valid for logged user.
     * thereafter, swap of params are done to ensure that uid1 < loggedID (due to friend_request table constraints for avoiding duplication).
     * <ul>Two action is performed on database.</ul>
     * <li>First two entries in user_friend is add in format (uid1,loggedId) and (loggedId,uid1) for ensuring benefit of indexing on a column.</li>
     * <li>After that entry from friend_request is removed for given uids.</li>
     *
     * @param uid1     id of user.
     * @param loggedId id of logged user.
     * @throws IllegalArgumentException if action is not valid.
     */
    @Transactional
    public ResponseEntity<String> acceptFriendReq(int uid1, int loggedId) {
        int loggedID = loggedId;
        try {
            if (validateFrAction(uid1, loggedId, true)) {
                if (uid1 > loggedId) {
                    int temp = uid1;
                    uid1 = loggedId;
                    loggedId = temp;
                }
                User_friend userFriend = new User_friend();
                User_friend userFriendReverse = new User_friend();
                userFriend.setUser_id(uid1);
                userFriendReverse.setFriend_id(uid1);
                userFriend.setFriend_id(loggedId);
                userFriendReverse.setUser_id(loggedId);
                userFriendRepo.saveAll(List.of(userFriend, userFriendReverse));
                deleteFriendRequest(uid1, loggedId);
                return new ResponseEntity<>("You are now friends", HttpStatus.OK);
            } else {
                throw new IllegalArgumentException("Argument not allowed");
            }
        } catch (IllegalArgumentException ie) {
            log.warn("user with id {} tried to perform illegal action. {}", loggedID, ie.getMessage());
            return new ResponseEntity<>("Action not allowed", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Deletes a friend request entry from the `friend_request` table, regardless of whether the request is
     * accepted or denied by the logged-in user.
     *
     * @param uid1     the user ID of the requester
     * @param loggedId the user ID of the logged-in user
     * @throws RuntimeException if an error occurs while interacting with the database
     */
    private ResponseEntity<String> deleteFriendRequest(int uid1, int loggedId) {
        try {
            friendReqRepo.deleteByUserIds(uid1, loggedId);
            log.warn("Friend Request of {} and {} is removed", uid1, loggedId);
            return new ResponseEntity<>("Friend Request Successfully Deleted",HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Error while deleting {} and {} friend_request.\n{}", uid1, loggedId, e.toString());
            return new ResponseEntity<>("Error occurred while deleting friend request",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Rejects a friend request after verifying that the action is permitted for the logged-in user.
     * This method first checks if the logged-in user is authorized to reject the friend request. If authorized,
     * it removes the corresponding entry from the {@link FriendReqRepo} table.
     *
     * @param uid1     the user ID of the requester
     * @param loggedId the user ID of the logged-in user
     * @throws IllegalArgumentException if the logged-in user is not authorized to perform the rejection as specified
     *                                  by {@link com.example.credit.connection.dto.FriendReqResponse}
     */
    public ResponseEntity<String> rejectFriendReq(int uid1, int loggedId) {
        if (validateFrAction(uid1, loggedId, true)) {
            if (uid1 > loggedId) {
                int temp = uid1;
                uid1 = loggedId;
                loggedId = temp;
            }
            return deleteFriendRequest(uid1, loggedId);
        } else {
            return new ResponseEntity<>("Action not allowed.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validates the action of accepting or denying a friend request.
     * This method checks whether the logged-in user is the sender of the request. Only the sender is allowed to
     * accept or deny the request. The method also accounts for the database constraint where `uid1 < uid2` to
     * avoid duplication in the `friend_request` table by swapping the user IDs if necessary.
     *
     * @param uid1 the user ID of the requester
     * @param uid2 the user ID of the logged-in user
     * @return {@code true} if the action specified by {@link com.example.credit.connection.dto.FriendReqResponse}
     * is allowed; {@code false} otherwise
     */
    private boolean validateFrAction(int uid1, int uid2, boolean receiver) {
        int loggerID = uid2;
        if (uid1 > uid2) {
            int temp = uid1;
            uid1 = uid2;
            uid2 = temp;
        }
        log.warn("uid1 is {} and uid2 is {} ", uid1, uid2);
        Optional<Friend_request> freq = friendReqRepo.findByUid1AndUid2(uid1, uid2);
        log.warn(freq.toString());
        if (freq.isEmpty()) return false;
        int uidTemp1 = freq.get().getUid1();
        int uidTemp2 = freq.get().getUid2();
        log.warn("Retrived uid1 is {} and uid2 is {}", uidTemp1, uidTemp2);
        String requestor = freq.get().getRequestor();
        log.warn("Requestor is {} and logged in ID is {}", requestor, loggerID);
        if (uidTemp1 == loggerID) {
            return receiver ? requestor.equals("UID2") : requestor.equals("UID1");
        } else if (uidTemp2 != loggerID) {
            return false;
        } else {
            return receiver ? requestor.equals("UID1") : requestor.equals("UID2");
        }
    }

    /**
     * Deletes a friend request sent by the sender to the receiver.
     * Validates that the action is permitted and ensures that only the sender can delete the request.
     *
     * @param receiverID the ID of the user who received the friend request.
     * @param senderId the ID of the user who sent the friend request.
     * @return a {@link ResponseEntity} containing a message indicating the result of the delete operation.
     *         - HTTP 200 (OK) if the friend request is successfully deleted.
     *         - HTTP 400 (Bad Request) if the action is not permitted.
     */
    public ResponseEntity<String> deleteSendRequest(int receiverID, int senderId) {
        if (validateFrAction(receiverID, senderId, false)) {
            if (receiverID > senderId) {
                int temp = receiverID;
                receiverID = senderId;
                senderId = temp;
            }
            log.warn("uid1 is {} and uid2 is {}.",receiverID,senderId);
            return deleteFriendRequest(receiverID, senderId);
        }
        return new ResponseEntity<>("Action not permitted.", HttpStatus.BAD_REQUEST);
    }

    // TODO: complete this method
    private boolean validateReadingRequest(int uid){
        return false;
    }

    // TODO: complete this
    public ResponseEntity<List<FriendListDto>> getFriendList(int uid, Pageable pageable){
       return null;
    }

    // TODO: complete this
    public ResponseEntity<List<FriendReqPendingDto>> getPendingRequest(int uid){
        return null;
    }
}