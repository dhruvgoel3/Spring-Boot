package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.ActivityRequest;
import com.project.Fitness_Tracker.DTO.ActivityResponse;
import com.project.Fitness_Tracker.entity.Activity;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.repository.ActivityRepository;


import com.project.Fitness_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid user: " + request.getUserId()));

        Activity activity = Activity.builder()
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        activity.setUser(user);

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponse(savedActivity);
    }


    public List<ActivityResponse> getUserActivities(String userid) {
        List<Activity> activityList = activityRepository.findByUserId(userid);
        return activityList.stream().map(this::mapToResponse).collect(Collectors.toList()
        );

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

