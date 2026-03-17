package com.practiceee.practicee.repositories;

import com.practiceee.practicee.entitiy.Notes;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, String> {


}
