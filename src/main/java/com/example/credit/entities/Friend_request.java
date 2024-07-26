package com.example.credit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
