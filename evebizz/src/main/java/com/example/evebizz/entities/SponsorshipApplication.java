package com.eventhub.entity;

import com.eventhub.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sponsorship_applications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SponsorshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private SponsorshipListing listing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_description", nullable = false, columnDefinition = "TEXT")
    private String eventDescription;

    @Column(name = "expected_participants")
    private Integer expectedParticipants;

    @Column(name = "proposal_text", nullable = false, columnDefinition = "TEXT")
    private String proposalText;

    @Column(name = "proposal_doc_url")
    private String proposalDocUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}