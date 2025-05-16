package com.infy.WebComic_Backend.entity;

//Page.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @JoinColumn(name = "chapter_id", nullable = false)
 private Chapter chapter;
 
 @Column(nullable = false)
 private int pageNumber;
 
 @Column(nullable = false)
 private String imageUrl;
}
