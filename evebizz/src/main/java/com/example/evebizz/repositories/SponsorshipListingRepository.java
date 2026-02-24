package com.example.evebizz.repositories;

import com.example.evebizz.entities.SponsorshipListing;
import com.example.evebizz.enums.ListingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorshipListingRepository extends JpaRepository<SponsorshipListing, Long>,
        JpaSpecificationExecutor<SponsorshipListing> {
    Page<SponsorshipListing> findByCompanyId(Long companyId, Pageable pageable);

    Page<SponsorshipListing> findByStatus(ListingStatus status, Pageable pageable);

    @Query("SELECT s FROM SponsorshipListing s WHERE s.status = 'ACTIVE' AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)")
    Page<SponsorshipListing> findActiveListings(Pageable pageable);

    long countByCompanyIdAndStatus(Long companyId, ListingStatus status);
}