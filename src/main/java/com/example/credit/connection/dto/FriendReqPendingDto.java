package com.example.credit.connection.dto;

import java.time.LocalDateTime;

public record FriendReqPendingDto(int id , String name , String email, LocalDateTime requestSent) {
}