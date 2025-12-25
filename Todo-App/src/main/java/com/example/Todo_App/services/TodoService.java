package com.example.Todo_App.services;

import com.example.Todo_App.entity.Todo;
import com.example.Todo_App.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        todoRepository.save(todo);
        return todo;

    }

    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        return todoRepository.save(existingTodo);

    }


}
