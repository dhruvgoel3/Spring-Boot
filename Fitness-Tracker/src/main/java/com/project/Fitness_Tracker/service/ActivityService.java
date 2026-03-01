package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.ActivityRequest;
import com.project.Fitness_Tracker.DTO.ActivityResponse;
import com.project.Fitness_Tracker.entity.Activity;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.repository.ActivityRepository;
import com.project.Fitness_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ActivityResponse trackActivity(String userId, ActivityRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Activity activity = Activity.builder()
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .user(user)
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId);
        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId, String userId) {
        Activity activity = activityRepository.findByIdAndUserId(activityId, userId)
                .orElseThrow(() -> new RuntimeException("Activity not found: " + activityId));
        return mapToResponse(activity);
    }

    public ActivityResponse updateActivity(String activityId, String userId, ActivityRequest request) {
        Activity activity = activityRepository.findByIdAndUserId(activityId, userId)
                .orElseThrow(() -> new RuntimeException("Activity not found: " + activityId));

        activity.setType(request.getType());
        activity.setDuration(request.getDuration());
        activity.setCaloriesBurned(request.getCaloriesBurned());
        activity.setStartTime(request.getStartTime());
        activity.setAdditionalMetrics(request.getAdditionalMetrics());

        Activity updatedActivity = activityRepository.save(activity);
        return mapToResponse(updatedActivity);
    }

    public void deleteActivity(String activityId, String userId) {
        Activity activity = activityRepository.findByIdAndUserId(activityId, userId)
                .orElseThrow(() -> new RuntimeException("Activity not found: " + activityId));
        activityRepository.delete(activity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = modelMapper.map(activity, ActivityResponse.class);
        response.setUserId(activity.getUser().getId());
        return response;
    }
}