package com.infy.WebComic_Backend.service;

//LikeService.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Like;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceNotFoundException;
import com.infy.WebComic_Backend.repository.ComicRepository;
import com.infy.WebComic_Backend.repository.LikeRepository;
import com.infy.WebComic_Backend.repository.UserRepository;

@Service
public class LikeService {

 @Autowired
 private LikeRepository likeRepository;

 @Autowired
 private ComicRepository comicRepository;

 @Autowired
 private UserRepository userRepository;

 @Transactional
 public void likeComic(Long comicId) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     // Check if user already liked the comic
     if (likeRepository.existsByUserAndComic(currentUser, comic)) {
         return; // Already liked, do nothing
     }
     
     // Create new like
     Like like = new Like();
     like.setUser(currentUser);
     like.setComic(comic);
     
     likeRepository.save(like);
     
     // Update comic likes count
     comic.setLikes(comic.getLikes() + 1);
     comicRepository.save(comic);
 }

 @Transactional
 public void unlikeComic(Long comicId) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     // Check if user liked the comic
     if (!likeRepository.existsByUserAndComic(currentUser, comic)) {
         return; // Not liked, do nothing
     }
     
     // Remove like
     likeRepository.deleteByUserAndComic(currentUser, comic);
     
     // Update comic likes count
     if (comic.getLikes() > 0) {
         comic.setLikes(comic.getLikes() - 1);
         comicRepository.save(comic);
     }
 }

 public boolean hasUserLikedComic(Long comicId) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     return likeRepository.existsByUserAndComic(currentUser, comic);
 }

 private User getCurrentUser() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}