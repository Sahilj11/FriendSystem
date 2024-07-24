package com.example.credit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** FriendReq_Sender */
@Entity
@Table(name = "friendreq_sender")
@Getter
@Setter
public class FriendReq_Sender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int senderId;
    private int receiverId;
    private int status;
}
