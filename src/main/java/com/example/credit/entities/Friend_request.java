package com.example.credit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_request")
@Getter
@Setter
public class Friend_request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int uid1;
    private int uid2;

    @Column(length = 4)
    private String requestor;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_date;

    @PrePersist
    private void onCreate(){
        created_date = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        created_date = LocalDateTime.now();
    }
}