package com.example.credit.repo;

import com.example.credit.entities.Friend_request;
import jakarta.transaction.Transactional;
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

    @Query("SELECT fr FROM Friend_request fr WHERE (fr.uid1 = :uid1 AND fr.uid2 = :uid2)")
    Optional<Friend_request> findByUids(int uid1,int uid2);
}