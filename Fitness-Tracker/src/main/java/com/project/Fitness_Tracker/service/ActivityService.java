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

    public ActivityResponse trackActivity(ActivityRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid user: " + request.getUserId()));

        Activity activity = modelMapper.map(request, Activity.class);
        activity.setUser(user);

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId);
        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = modelMapper.map(activity, ActivityResponse.class);
        response.setUserId(activity.getUser().getId());
        return response;
    }
}