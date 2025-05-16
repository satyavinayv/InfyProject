package com.infy.WebComic_Backend.entity;

//Subscription.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @JoinColumn(name = "subscriber_id", nullable = false)
 private User subscriber;
 
 @ManyToOne
 @JoinColumn(name = "creator_id", nullable = false)
 private User creator;
 
 @Column(nullable = false)
 private LocalDateTime startDate;
 
 private LocalDateTime endDate;
 
 private boolean active;
 
 @Column(nullable = false)
 private double amount;
 
 @PrePersist
 protected void onCreate() {
     startDate = LocalDateTime.now();
     active = true;
 }
}
