package com.example.credit.connection.dto;

import java.time.LocalDateTime;

public record FriendListDto(int uid , String name , String email, LocalDateTime created_date) {
}