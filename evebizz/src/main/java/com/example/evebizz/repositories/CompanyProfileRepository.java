package com.example.evebizz.repositories;

import com.example.evebizz.entities.CompanyProfile;
import com.example.evebizz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {
    Optional<CompanyProfile> findByUser(User user);

    Optional<CompanyProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}