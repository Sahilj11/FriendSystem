package com.example.credit.repo;

import com.example.credit.entities.User_friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendRepo extends JpaRepository<User_friend, Integer> {

    Page<User_friend> findByUserId(int userId, Pageable pageable);
}