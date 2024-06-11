package com.example.credit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** BillEntity */
@Entity
@Table(name = "bills_table")
@Getter
@Setter
public class BillEntity {

  @Id private int billId;

  private int sellerId;

  @Column(nullable = false)
  private int buyerId;

  @Column(columnDefinition = "json")
  private String content;

  private float billAmount;

  @Column(nullable = false)
  private int status;
}
