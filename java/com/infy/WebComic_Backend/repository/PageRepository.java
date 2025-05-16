package com.infy.WebComic_Backend.repository;

//PageRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Chapter;
import com.infy.WebComic_Backend.entity.Page;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
 List<Page> findByChapterOrderByPageNumberAsc(Chapter chapter);
}
