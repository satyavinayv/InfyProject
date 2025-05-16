package com.infy.WebComic_Backend.repository;

//CommentRepository.java

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
 Page<Comment> findByComicOrderByCreatedAtDesc(Comic comic, Pageable pageable);
}
