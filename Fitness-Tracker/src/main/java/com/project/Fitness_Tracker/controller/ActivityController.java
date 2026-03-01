package com.project.Fitness_Tracker.controller;

import com.project.Fitness_Tracker.DTO.ActivityRequest;
import com.project.Fitness_Tracker.DTO.ActivityResponse;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.service.ActivityService;
import com.project.Fitness_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/activities")
@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ActivityRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(activityService.trackActivity(user.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(activityService.getUserActivities(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(activityService.getActivityById(id, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @RequestBody ActivityRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(activityService.updateActivity(id, user.getId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        activityService.deleteActivity(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}