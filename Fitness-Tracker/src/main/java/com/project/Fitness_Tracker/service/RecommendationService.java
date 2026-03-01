package com.project.Fitness_Tracker.service;

import com.project.Fitness_Tracker.DTO.RecommendationRequest;
import com.project.Fitness_Tracker.DTO.RecommendationResponse;
import com.project.Fitness_Tracker.entity.Activity;
import com.project.Fitness_Tracker.entity.Recommendation;
import com.project.Fitness_Tracker.entity.User;
import com.project.Fitness_Tracker.repository.ActivityRepository;
import com.project.Fitness_Tracker.repository.RecommendationRepository;
import com.project.Fitness_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;
    private final ModelMapper modelMapper;

    public RecommendationResponse createRecommendation(String userId, RecommendationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Activity activity = activityRepository.findByIdAndUserId(request.getActivityId(), userId)
                .orElseThrow(() -> new RuntimeException("Activity not found: " + request.getActivityId()));

        Recommendation recommendation = new Recommendation();
        recommendation.setType(request.getType());
        recommendation.setRecommendation(request.getRecommendation());
        recommendation.setImprovements(request.getImprovements());
        recommendation.setSuggestions(request.getSuggestions());
        recommendation.setSafety(request.getSafety());
        recommendation.setUser(user);
        recommendation.setActivity(activity);
        recommendation.setCreatedAt(LocalDateTime.now());

        Recommendation saved = recommendationRepository.save(recommendation);
        return mapToResponse(saved);
    }

    public List<RecommendationResponse> getUserRecommendations(String userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);
        return recommendations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RecommendationResponse> getActivityRecommendations(String activityId) {
        List<Recommendation> recommendations = recommendationRepository.findByActivityId(activityId);
        return recommendations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RecommendationResponse mapToResponse(Recommendation recommendation) {
        RecommendationResponse response = modelMapper.map(recommendation, RecommendationResponse.class);
        response.setActivityId(recommendation.getActivity().getId());
        response.setUserId(recommendation.getUser().getId());
        return response;
    }
}
