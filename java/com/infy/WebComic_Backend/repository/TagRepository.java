package com.infy.WebComic_Backend.repository;

//TagRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
 Optional<Tag> findByNameIgnoreCase(String name);
}
