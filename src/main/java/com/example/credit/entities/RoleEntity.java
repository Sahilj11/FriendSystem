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
 * RoleEntity
 */
@Entity
@Table(name = "roles")
@Getter
public class RoleEntity {

   @Id
   @Column(name = "role_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(name = "rolename")
   private String roleName;
}
