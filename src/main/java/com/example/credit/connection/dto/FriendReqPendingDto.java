package com.example.credit.connection.dto;

import java.util.Date;

public record FriendReqPendingDto(int id , String name , String email, Date requestSent) {
}