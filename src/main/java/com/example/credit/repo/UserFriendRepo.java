package com.example.credit.repo;

import com.example.credit.entities.User_friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendRepo extends JpaRepository<User_friend, Integer> {
}