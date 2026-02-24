package com.example.evebizz.repositories;

import com.example.evebizz.entities.ClubProfile;
import com.example.evebizz.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubProfileRepository extends JpaRepository<ClubProfile, Long> {
    Optional<ClubProfile> findByUser(User user);

    Optional<ClubProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Page<ClubProfile> findByUniversityContainingIgnoreCase(String university, Pageable pageable);

    Page<ClubProfile> findByCityIgnoreCase(String city, Pageable pageable);
}