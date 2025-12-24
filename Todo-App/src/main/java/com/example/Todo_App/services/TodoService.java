package com.example.Todo_App.services;

import com.example.Todo_App.entity.Todo;
import com.example.Todo_App.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    public void getTodoById(Long id) {
        todoRepository.findAllById(Collections.singleton(id));

    }


}
