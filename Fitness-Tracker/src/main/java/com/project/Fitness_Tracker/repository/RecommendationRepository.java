package com.project.Fitness_Tracker.repository;

import com.project.Fitness_Tracker.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    List<Recommendation> findByUserId(String userId);

    List<Recommendation> findByActivityId(String activityId);
}
