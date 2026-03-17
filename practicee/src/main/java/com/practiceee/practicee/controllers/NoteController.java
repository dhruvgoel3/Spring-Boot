package com.practiceee.practicee.controllers;

import com.practiceee.practicee.entitiy.Notes;
import com.practiceee.practicee.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public Notes createNote(@RequestBody Notes notes) {
        Notes response = noteService.createNote(notes);
        return response;
    }

    @GetMapping
    public Notes getAllNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);

    }

    @GetMapping
    public List<Notes> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PutMapping
    public Notes updateNote(@PathVariable String noteId, @RequestBody Notes note) {
        return noteService.updateNote(noteId, note);
    }

    @DeleteMapping
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }


}
