package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.ActivityRequest;
import com.project.Fitness_Tracker.DTO.ActivityResponse;
import com.project.Fitness_Tracker.entity.Activity;
import com.project.Fitness_Tracker.repository.ActivityRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {

        Activity activity = Activity.builder()
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .user(request.getUser()) // ideally fetch from DB
                .build();

        activityRepository.save(activity);

        return mapToResponse(activity);
    }

    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ActivityResponse mapToResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurned(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .additionalMetrics(activity.getAdditionalMetrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}

