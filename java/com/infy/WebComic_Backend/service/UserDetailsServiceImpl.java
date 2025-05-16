package com.infy.WebComic_Backend.service;

//UserDetailsServiceImpl.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.repository.UserRepository;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

 @Autowired
 private UserRepository userRepository;

 @Override
 @Transactional
 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user = userRepository.findByEmail(email)
             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

     return new org.springframework.security.core.userdetails.User(
             user.getEmail(),
             user.getPassword(),
             Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
     );
 }
}
