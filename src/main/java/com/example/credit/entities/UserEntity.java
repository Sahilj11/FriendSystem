package com.example.credit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Maps to users table {@link ManyToOne} with {@link RoleEntity}
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "roleid")
  private RoleEntity role;

  private String password;

  private String uuid;

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
