package com.example.evebizz.repositories;

import com.example.evebizz.entities.ExpertInvitation;
import com.example.evebizz.enums.InvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertInvitationRepository extends JpaRepository<ExpertInvitation, Long> {
    Page<ExpertInvitation> findByExpertId(Long expertId, Pageable pageable);
    Page<ExpertInvitation> findByOrganizerId(Long organizerId, Pageable pageable);
    long countByExpertIdAndStatus(Long expertId, InvitationStatus status);
}