package com.infy.WebComic_Backend.repository;

//LikeRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Like;
import com.infy.WebComic_Backend.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
 boolean existsByUserAndComic(User user, Comic comic);
 void deleteByUserAndComic(User user, Comic comic);
 int countByComic(Comic comic);
}