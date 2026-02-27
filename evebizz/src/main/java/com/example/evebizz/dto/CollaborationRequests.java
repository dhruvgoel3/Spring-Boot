package com.example.evebizz.dto;

import com.example.evebizz.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

public class CollaborationRequests {

    @Data
    public static class CreateCollaborationRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String eventName;
        @NotBlank
        private String eventDescription;
        private LocalDate eventDate;
        @NotNull
        private EventType eventType;
        @NotBlank
        private String whatWeOffer;
        @NotBlank
        private String whatWeNeed;
        private Integer expectedParticipants;
        private String location;
        private boolean isRemote;
        private LocalDate deadline;
    }

    @Data
    public static class ExpressInterestRequest {
        private String message;
    }
}