package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.repository.ActivityRepository;
import com.project.Fitness_Tracker.repository.RecommendationRepository;
import com.project.Fitness_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;
}
