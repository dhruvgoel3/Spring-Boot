package com.project.Fitness_Tracker.repository;

import com.project.Fitness_Tracker.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByUserId(String userId);

    Optional<Activity> findByIdAndUserId(String id, String userId);
}
