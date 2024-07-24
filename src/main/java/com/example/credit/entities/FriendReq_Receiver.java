package com.example.credit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** FriendReq_Receiver */
@Entity
@Table(name = "friendreq_receiver")
@Getter
@Setter
public class FriendReq_Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int receiverId;
    private int senderId;
    private int status;
}
