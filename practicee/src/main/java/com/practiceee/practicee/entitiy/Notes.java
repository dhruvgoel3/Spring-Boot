package com.practiceee.practicee.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notes {
    @Id
    private String id;
    private String title;
    private String content;
    private Date addedDate;
    private boolean live = false;
}
