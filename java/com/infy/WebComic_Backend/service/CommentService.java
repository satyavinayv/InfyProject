package com.infy.WebComic_Backend.service;

//CommentService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WebComic_Backend.dto.CommentCreateDTO;
import com.infy.WebComic_Backend.dto.CommentDTO;
import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Comment;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceNotFoundException;
import com.infy.WebComic_Backend.exception.UnauthorizedException;
import com.infy.WebComic_Backend.repository.ComicRepository;
import com.infy.WebComic_Backend.repository.CommentRepository;
import com.infy.WebComic_Backend.repository.UserRepository;

@Service
public class CommentService {

 @Autowired
 private CommentRepository commentRepository;

 @Autowired
 private ComicRepository comicRepository;

 @Autowired
 private UserRepository userRepository;

 @Autowired
 private ModelMapper modelMapper;

 @Transactional
 public CommentDTO createComment(Long comicId, CommentCreateDTO commentCreateDTO) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     Comment comment = new Comment();
     comment.setComic(comic);
     comment.setUser(currentUser);
     comment.setContent(commentCreateDTO.getContent());
     
     Comment savedComment = commentRepository.save(comment);
     
     return mapCommentToDTO(savedComment);
 }

 public Page<CommentDTO> getCommentsByComicId(Long comicId, Pageable pageable) {
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     Page<Comment> comments = commentRepository.findByComicOrderByCreatedAtDesc(comic, pageable);
     
     return comments.map(this::mapCommentToDTO);
 }

 @Transactional
 public void deleteComment(Long commentId) {
     User currentUser = getCurrentUser();
     
     Comment comment = commentRepository.findById(commentId)
             .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
     
     // Check if user is the author of the comment or the creator of the comic
     if (!comment.getUser().getId().equals(currentUser.getId()) && 
         !comment.getComic().getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only delete your own comments or comments on your comics");
     }
     
     commentRepository.delete(comment);
 }

 private CommentDTO mapCommentToDTO(Comment comment) {
     CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
     commentDTO.setComicId(comment.getComic().getId());
     commentDTO.setUserId(comment.getUser().getId());
     commentDTO.setUsername(comment.getUser().getUsername());
     
     return commentDTO;
 }

 private User getCurrentUser() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}