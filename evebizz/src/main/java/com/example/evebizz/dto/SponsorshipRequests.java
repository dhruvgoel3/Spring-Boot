package com.example.evebizz.dto;

import com.example.evebizz.enums.ListingStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SponsorshipRequests {

    @Data
    public static class CreateListingRequest {
        @NotBlank(message = "Title is required")
        @Size(max = 255)
        private String title;

        @NotBlank(message = "Description is required")
        private String description;

        @DecimalMin("0")
        private BigDecimal budgetMin;

        @DecimalMin("0")
        private BigDecimal budgetMax;

        private String currency = "INR";
        private List<String> eventTypes;
        private List<String> industriesPreferred;
        private String locationPreference;
        private boolean isRemoteAllowed = true;

        @NotBlank(message = "Please specify what you will offer")
        private String whatWeOffer;

        @NotBlank(message = "Please specify what you expect")
        private String whatWeExpect;

        private Integer maxApplications;
        private LocalDate deadline;
    }

    @Data
    public static class UpdateListingRequest {
        private String title;
        private String description;
        private BigDecimal budgetMin;
        private BigDecimal budgetMax;
        private String currency;
        private List<String> eventTypes;
        private List<String> industriesPreferred;
        private String locationPreference;
        private Boolean isRemoteAllowed;
        private String whatWeOffer;
        private String whatWeExpect;
        private Integer maxApplications;
        private LocalDate deadline;
        private ListingStatus status;
    }

    @Data
    public static class CreateApplicationRequest {
        @NotBlank(message = "Event name is required")
        private String eventName;

        private LocalDate eventDate;

        @NotBlank(message = "Event description is required")
        private String eventDescription;

        private Integer expectedParticipants;

        @NotBlank(message = "Proposal text is required")
        private String proposalText;

        private String proposalDocUrl;
    }

    @Data
    public static class ReviewApplicationRequest {
        @NotNull
        private boolean accepted;
        private String rejectionReason; // required if rejected
    }
}