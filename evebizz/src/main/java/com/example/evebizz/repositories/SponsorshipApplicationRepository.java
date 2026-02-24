package com.example.evebizz.repositories;

import com.example.evebizz.entities.SponsorshipApplication;
import com.example.evebizz.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SponsorshipApplicationRepository extends JpaRepository<SponsorshipApplication, Long> {
    Page<SponsorshipApplication> findByOrganizerId(Long organizerId, Pageable pageable);
    Page<SponsorshipApplication> findByListingId(Long listingId, Pageable pageable);
    Optional<SponsorshipApplication> findByListingIdAndOrganizerId(Long listingId, Long organizerId);
    boolean existsByListingIdAndOrganizerId(Long listingId, Long organizerId);
    long countByListingId(Long listingId);
    long countByOrganizerIdAndStatus(Long organizerId, ApplicationStatus status);
}