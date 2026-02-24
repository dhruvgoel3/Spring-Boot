package com.example.evebizz.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sponsorship_listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SponsorshipListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyProfile company;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "budget_min", precision = 12, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 12, scale = 2)
    private BigDecimal budgetMax;

    @Builder.Default
    private String currency = "INR";

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "event_types", columnDefinition = "json")
    private List<String> eventTypes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "industries_preferred", columnDefinition = "json")
    private List<String> industriesPreferred;

    @Column(name = "location_preference")
    private String locationPreference;

    @Builder.Default
    @Column(name = "is_remote_allowed")
    private boolean isRemoteAllowed = true;

    @Column(name = "what_we_offer", nullable = false, columnDefinition = "TEXT")
    private String whatWeOffer;

    @Column(name = "what_we_expect", nullable = false, columnDefinition = "TEXT")
    private String whatWeExpect;

    @Column(name = "max_applications")
    private Integer maxApplications;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ListingStatus status = ListingStatus.ACTIVE;

    @Builder.Default
    @Column(name = "view_count")
    private int viewCount = 0;

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