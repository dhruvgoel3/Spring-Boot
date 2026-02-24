package com.example.evebizz.entities;

import com.example.evebizz.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expert_invitations")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ExpertInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)
    private com.eventhub.entity.ExpertProfile expert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_location")
    private String eventLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_requested", nullable = false)
    private ExpertRole roleRequested;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "response_message", columnDefinition = "TEXT")
    private String responseMessage;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

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