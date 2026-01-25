package com.project.Fitness_Tracker.repository;

import com.project.Fitness_Tracker.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
