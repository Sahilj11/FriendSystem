package com.example.credit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_friend")
@Getter
@Setter
public class User_friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private int friend_id;

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
