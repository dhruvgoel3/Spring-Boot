package com.project.Fitness_Tracker.repository;

import com.project.Fitness_Tracker.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, String> {

}
