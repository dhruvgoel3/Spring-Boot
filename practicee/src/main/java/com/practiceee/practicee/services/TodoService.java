package com.practiceee.practicee.services;

import com.practiceee.practicee.entitiy.Todo;
import com.practiceee.practicee.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    @Autowired
    private final TodoRepository todoRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todo) {
        Optional<Todo> response = todoRepository.findById(id);

        if (response.isPresent()) {
            Todo existingtodo = response.get();
            existingtodo.setTitle(todo.getTitle());
            existingtodo.setDescription(todo.getDescription());
            existingtodo.setTitle(todo.getTitle());

            return todoRepository.save(existingtodo);

        } else {
            throw new RuntimeException("Todo not found");
        }
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public void deleteById(Long id) {
        todoRepository.findById(id);
    }
}
