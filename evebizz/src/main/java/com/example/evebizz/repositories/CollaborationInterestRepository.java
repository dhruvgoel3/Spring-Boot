package com.example.evebizz.repositories;

import com.example.evebizz.entities.CollaborationInterest;
import com.example.evebizz.enums.InterestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollaborationInterestRepository extends JpaRepository<CollaborationInterest, Long> {
    Page<CollaborationInterest> findByRequestId(Long requestId, Pageable pageable);

    Page<CollaborationInterest> findByInterestedClubId(Long clubId, Pageable pageable);

    Optional<CollaborationInterest> findByRequestIdAndInterestedClubId(Long requestId, Long clubId);

    boolean existsByRequestIdAndInterestedClubId(Long requestId, Long clubId);

    long countByRequestIdAndStatus(Long requestId, InterestStatus status);
}