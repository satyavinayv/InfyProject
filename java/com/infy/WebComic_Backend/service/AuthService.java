package com.infy.WebComic_Backend.service;

//AuthService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.WebComic_Backend.dto.JwtResponseDTO;
import com.infy.WebComic_Backend.dto.LoginDTO;
import com.infy.WebComic_Backend.dto.UserDTO;
import com.infy.WebComic_Backend.dto.UserRegistrationDTO;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceAlreadyExistsException;
import com.infy.WebComic_Backend.repository.UserRepository;
import com.infy.WebComic_Backend.security.JwtTokenProvider;

@Service
public class AuthService {

 @Autowired
 private UserRepository userRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @Autowired
 private AuthenticationManager authenticationManager;

 @Autowired
 private JwtTokenProvider tokenProvider;

 @Autowired
 private ModelMapper modelMapper;

 public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
     // Check if username already exists
     if (userRepository.existsByUsername(registrationDTO.getUsername())) {
         throw new ResourceAlreadyExistsException("Username is already taken");
     }

     // Check if email already exists
     if (userRepository.existsByEmail(registrationDTO.getEmail())) {
         throw new ResourceAlreadyExistsException("Email is already in use");
     }

     // Create new user
     User user = new User();
     user.setUsername(registrationDTO.getUsername());
     user.setEmail(registrationDTO.getEmail());
     user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
     user.setRole(registrationDTO.getRole());

     User savedUser = userRepository.save(user);

     return modelMapper.map(savedUser, UserDTO.class);
 }

 public JwtResponseDTO authenticateUser(LoginDTO loginDTO) {
     Authentication authentication = authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(
                     loginDTO.getEmail(),
                     loginDTO.getPassword()
             )
     );

     SecurityContextHolder.getContext().setAuthentication(authentication);
     String jwt = tokenProvider.generateToken(authentication);
     
     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     User user = userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new RuntimeException("User not found"));

     return new JwtResponseDTO(
             jwt,
             user.getId(),
             user.getUsername(),
             user.getEmail(),
             user.getRole()
     );
 }
}
