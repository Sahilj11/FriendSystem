package com.example.credit.repo;

import com.example.credit.entities.Friend_request;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendReqRepo extends JpaRepository<Friend_request,Integer> {
    boolean existsByUid1(int uid1);
    boolean existsByUid2(int uid2);

    @Modifying
    @Transactional
    @Query("DELETE FROM Friend_request fr WHERE (fr.uid1 = :uid1 AND fr.uid2 = :uid2)")
    void deleteByUserIds(int uid1, int uid2);

    Optional<Friend_request> findByUid1AndUid2(int uid1, int uid2);

    @Transactional
    @Query("SELECT fr FROM Friend_request fr WHERE (fr.uid1 = :uid AND fr.requestor = 'UID2') OR (fr.uid2 = :uid AND fr.requestor = 'UID1')")
    Page<Friend_request> findAllByUidAndRequestor(int uid, Pageable pageable);

    @Transactional
    @Query("SELECT fr FROM Friend_request fr WHERE (fr.uid1 = :uid AND fr.requestor = 'UID1') OR (fr.uid2 = :uid AND fr.requestor = 'UID2')")
    Page<Friend_request> findAllByUidAndSentRequestor(int uid, Pageable pageable);
}