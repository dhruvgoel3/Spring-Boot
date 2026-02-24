package com.example.evebizz.entities;

import com.example.evebizz.enums.ClubType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "club_profiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClubProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "club_name", nullable = false)
    private String clubName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String university;

    private String city;
    private String state;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "member_count")
    private Integer memberCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_type")
    private ClubType clubType;

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