package com.example.evebizz.dto;

import com.example.evebizz.enums.ExpertRole;
import com.example.evebizz.enums.FeeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ExpertRequests {

    @Data
    public static class CreateExpertProfileRequest {
        @NotBlank
        private String bio;
        private String headline;
        private String company;
        private String designation;
        private String location;
        private String linkedinUrl;
        private String twitterUrl;
        private String websiteUrl;
        private Integer yearsExperience;
        private List<String> rolesOffered;
        private List<String> topics;
        private List<String> eventTypes;
        private List<String> tags;
        private FeeType feeType;
    }

    @Data
    public static class SendInvitationRequest {
        @NotNull
        private Long expertId;
        @NotBlank
        private String eventName;
        private LocalDate eventDate;
        private String eventLocation;
        @NotNull
        private ExpertRole roleRequested;
        @NotBlank
        private String message;
    }

    @Data
    public static class RespondInvitationRequest {
        @NotNull
        private boolean accepted;
        private String responseMessage;
    }
}