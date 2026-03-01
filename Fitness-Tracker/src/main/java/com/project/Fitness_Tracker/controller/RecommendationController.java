package com.project.Fitness_Tracker.controller;

import com.project.Fitness_Tracker.DTO.RecommendationRequest;
import com.project.Fitness_Tracker.DTO.RecommendationResponse;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.service.RecommendationService;
import com.project.Fitness_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recommendations")
@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RecommendationResponse> createRecommendation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecommendationRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(recommendationService.createRecommendation(user.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<RecommendationResponse>> getUserRecommendations(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(recommendationService.getUserRecommendations(user.getId()));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<RecommendationResponse>> getActivityRecommendations(
            @PathVariable String activityId) {
        return ResponseEntity.ok(recommendationService.getActivityRecommendations(activityId));
    }
}
