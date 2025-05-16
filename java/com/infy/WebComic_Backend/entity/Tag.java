package com.infy.WebComic_Backend.entity;

//Tag.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @Column(nullable = false, unique = true)
 private String name;
 
 @ManyToMany(mappedBy = "tags")
 private Set<Comic> comics = new HashSet<>();
}