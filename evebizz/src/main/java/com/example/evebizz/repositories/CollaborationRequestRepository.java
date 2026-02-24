package com.example.evebizz.repositories;

import com.example.evebizz.entities.CollaborationRequest;
import com.example.evebizz.enums.CollaborationStatus;
import com.example.evebizz.enums.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long>,
        JpaSpecificationExecutor<CollaborationRequest> {
    Page<CollaborationRequest> findByClubId(Long clubId, Pageable pageable);
    Page<CollaborationRequest> findByStatus(CollaborationStatus status, Pageable pageable);
    Page<CollaborationRequest> findByEventType(EventType eventType, Pageable pageable);
}