package com.example.evebizz.entities;

import com.example.evebizz.enums.FeeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "expert_profiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ExpertProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    private String headline;
    private String company;
    private String designation;
    private String location;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Builder.Default
    @Column(name = "is_available")
    private boolean isAvailable = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "roles_offered", columnDefinition = "json")
    private List<String> rolesOffered;  // ["SPEAKER","JUDGE","MENTOR"]

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> topics;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "event_types", columnDefinition = "json")
    private List<String> eventTypes;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type")
    @Builder.Default
    private FeeType feeType = FeeType.NEGOTIABLE;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpertTag> tags;

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