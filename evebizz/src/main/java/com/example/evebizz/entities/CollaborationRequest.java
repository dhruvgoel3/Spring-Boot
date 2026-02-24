package com.example.evebizz.entities;

import com.example.evebizz.enums.CollaborationStatus;
import com.example.evebizz.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "collaboration_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CollaborationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private ClubProfile club;

    @Column(nullable = false)
    private String title;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_description", nullable = false, columnDefinition = "TEXT")
    private String eventDescription;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "what_we_offer", nullable = false, columnDefinition = "TEXT")
    private String whatWeOffer;

    @Column(name = "what_we_need", nullable = false, columnDefinition = "TEXT")
    private String whatWeNeed;

    @Column(name = "expected_participants")
    private Integer expectedParticipants;

    private String location;

    @Builder.Default
    @Column(name = "is_remote")
    private boolean isRemote = false;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CollaborationStatus status = CollaborationStatus.OPEN;

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