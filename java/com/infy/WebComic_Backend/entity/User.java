package com.infy.WebComic_Backend.entity;

//User.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @Column(nullable = false, unique = true)
 private String username;
 
 @Column(nullable = false, unique = true)
 private String email;
 
 @Column(nullable = false)
 private String password;
 
 @Column(nullable = false)
 private String role; // ROLE_READER or ROLE_CREATOR
 
 private String bio;
 
 private String avatarUrl;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 private LocalDateTime updatedAt;
 
 @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
 private Set<Comic> comics = new HashSet<>();
 
 @ManyToMany
 @JoinTable(
     name = "user_follows",
     joinColumns = @JoinColumn(name = "follower_id"),
     inverseJoinColumns = @JoinColumn(name = "followed_id")
 )
 private Set<User> following = new HashSet<>();
 
 @ManyToMany(mappedBy = "following")
 private Set<User> followers = new HashSet<>();
 
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
 }
 
 @PreUpdate
 protected void onUpdate() {
     updatedAt = LocalDateTime.now();
 }
}