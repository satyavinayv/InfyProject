package com.infy.WebComic_Backend.service;

//UserService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WebComic_Backend.dto.UserDTO;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceNotFoundException;
import com.infy.WebComic_Backend.repository.UserRepository;

@Service
public class UserService {

 @Autowired
 private UserRepository userRepository;

 @Autowired
 private ModelMapper modelMapper;

 public UserDTO getCurrentUser() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     User user = userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
     
     UserDTO userDTO = modelMapper.map(user, UserDTO.class);
     userDTO.setFollowersCount(user.getFollowers().size());
     userDTO.setFollowingCount(user.getFollowing().size());
     
     return userDTO;
 }

 public UserDTO getUserById(Long id) {
     User user = userRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
     
     UserDTO userDTO = modelMapper.map(user, UserDTO.class);
     userDTO.setFollowersCount(user.getFollowers().size());
     userDTO.setFollowingCount(user.getFollowing().size());
     
     return userDTO;
 }

 @Transactional
 public void followUser(Long userId) {
     User currentUser = getCurrentUserEntity();
     User userToFollow = userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
     
     if (currentUser.getId().equals(userId)) {
         throw new IllegalArgumentException("You cannot follow yourself");
     }
     
     currentUser.getFollowing().add(userToFollow);
     userRepository.save(currentUser);
 }

 @Transactional
 public void unfollowUser(Long userId) {
     User currentUser = getCurrentUserEntity();
     User userToUnfollow = userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
     
     currentUser.getFollowing().remove(userToUnfollow);
     userRepository.save(currentUser);
 }

 private User getCurrentUserEntity() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}
