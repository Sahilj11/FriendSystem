package com.example.credit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * ConnectionEntity
 */
@Entity
@Table(name = "connections_table")
@Getter
@Setter
public class ConnectionEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int connId;

   @Column(nullable = false)
   private int useroneId;

   @Column(nullable = false)
   private int usertwoId;

   @Column(nullable = false)
   private int status;
}
