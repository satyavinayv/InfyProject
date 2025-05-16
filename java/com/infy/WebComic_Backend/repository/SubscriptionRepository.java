package com.infy.WebComic_Backend.repository;

//SubscriptionRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.WebComic_Backend.entity.Subscription;
import com.infy.WebComic_Backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
 List<Subscription> findBySubscriberAndActiveTrue(User subscriber);
 List<Subscription> findByCreatorAndActiveTrue(User creator);
 Optional<Subscription> findBySubscriberAndCreatorAndActiveTrue(User subscriber, User creator);
 int countByCreatorAndActiveTrue(User creator);
}
