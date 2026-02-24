package com.example.evebizz.repositories;

import com.example.evebizz.entities.ExpertProfile;
import com.example.evebizz.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long>,
        JpaSpecificationExecutor<ExpertProfile> {
    Optional<ExpertProfile> findByUser(User user);
    Optional<ExpertProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    @Query("SELECT e FROM ExpertProfile e WHERE e.isAvailable = true")
    Page<ExpertProfile> findAvailableExperts(Pageable pageable);
}