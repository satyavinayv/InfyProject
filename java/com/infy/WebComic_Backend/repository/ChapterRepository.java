package com.infy.WebComic_Backend.repository;

//ChapterRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Chapter;
import com.infy.WebComic_Backend.entity.Comic;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
 List<Chapter> findByComicOrderByOrderAsc(Comic comic);
 int countByComic(Comic comic);
}
