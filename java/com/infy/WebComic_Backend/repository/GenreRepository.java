package com.infy.WebComic_Backend.repository;

//GenreRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
 Optional<Genre> findByNameIgnoreCase(String name);
}
