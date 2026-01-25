package com.project.Fitness_Tracker.DTO;

import com.project.Fitness_Tracker.entity.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityRequest {
    private String userId;
    private ActivityType type;
    private Map<String, Object> additionalMetrics;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

}
