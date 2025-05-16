package com.infy.WebComic_Backend.repository;

//ComicRepository.java

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Genre;
import com.infy.WebComic_Backend.entity.Tag;
import com.infy.WebComic_Backend.entity.User;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Long> {
 Page<Comic> findByCreator(User creator, Pageable pageable);
 
 Page<Comic> findByGenresContaining(Genre genre, Pageable pageable);
 
 Page<Comic> findByTagsContaining(Tag tag, Pageable pageable);
 
 @Query("SELECT c FROM Comic c WHERE " +
        "LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(c.creator.username) LIKE LOWER(CONCAT('%', :query, '%'))")
 Page<Comic> search(String query, Pageable pageable);
 
 @Query("SELECT c FROM Comic c ORDER BY c.views DESC")
 List<Comic> findTrendingComics(Pageable pageable);
 
 @Query("SELECT c FROM Comic c ORDER BY c.createdAt DESC")
 Page<Comic> findRecentComics(Pageable pageable);
}