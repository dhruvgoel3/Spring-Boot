package com.practiceee.practicee.services;

import com.practiceee.practicee.entitiy.Notes;
import com.practiceee.practicee.repositories.NotesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NotesRepository notesRepository;


    public Notes createNote(Notes note) {
        note.setId(UUID.randomUUID().toString());
        return notesRepository.save(note);
    }

    public Notes getNoteById(String id) {
        return notesRepository.findById(id).orElseThrow(() -> new RuntimeException("Not not found"));
    }

    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    public Notes updateNote(String noteId, Notes note) {
        Notes existingNote = notesRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Not not found"));
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        existingNote.setLive(note.isLive());
        return notesRepository.save(note);


    }

    public void deleteNote(String id) {
        notesRepository.deleteById(id);
    }

}
