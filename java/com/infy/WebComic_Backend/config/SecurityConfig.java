package com.infy.WebComic_Backend.config;

//SecurityConfig.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.infy.WebComic_Backend.security.JwtAuthenticationEntryPoint;
import com.infy.WebComic_Backend.security.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

 @Autowired
 private JwtAuthenticationEntryPoint unauthorizedHandler;

 @Bean
 public JwtAuthenticationFilter jwtAuthenticationFilter() {
     return new JwtAuthenticationFilter();
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
     return authConfig.getAuthenticationManager();
 }

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http
         .cors().and().csrf().disable()
         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
         .authorizeHttpRequests()
             .requestMatchers("/api/auth/**").permitAll()
             .requestMatchers("/api/comics/public/**").permitAll()
             .requestMatchers("/api/genres/**").permitAll()
             .requestMatchers("/api/tags/**").permitAll()
             .requestMatchers("/v3/api-docs/**",
                     "/swagger-ui.html",
                     "/swagger-ui/**",
                     "/swagger-resources/**",
                     "/webjars/**").permitAll()
             .anyRequest().authenticated();

     http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

     return http.build();
 }

 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
     CorsConfiguration configuration = new CorsConfiguration();
     configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
     configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
     configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
     configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     source.registerCorsConfiguration("/**", configuration);
     return source;
 }
}
