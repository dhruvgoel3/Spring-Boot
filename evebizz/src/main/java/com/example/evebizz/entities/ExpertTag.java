package com.example.evebizz.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expert_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpertTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)

    @Column(nullable = false)
    private String tag;
}