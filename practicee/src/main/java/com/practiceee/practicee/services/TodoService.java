package com.practiceee.practicee.services;

import com.practiceee.practicee.entitiy.Todo;
import com.practiceee.practicee.entitiy.User;
import com.practiceee.practicee.repositories.TodoRepository;
import com.practiceee.practicee.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    // CREATE TODO
    public Todo createTodo(Todo todo, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUser(user);

        return todoRepository.save(todo);
    }


    // UPDATE TODO
    public Todo updateTodo(Long userId, Long todoId, Todo todo) {

        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!existingTodo.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("This todo does not belong to this user");
        }

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());

        return todoRepository.save(existingTodo);
    }


    // GET ALL TODOS OF A USER
    public List<Todo> getAllTodosOfUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return todoRepository.findByUserUserId(user.getUserId());
    }


    // GET TODO BY ID
    public Todo getTodoById(Long todoId, Long userId) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("This todo does not belong to this user");
        }

        return todo;
    }


    // DELETE TODO
    public void deleteById(Long userId, Long todoId) {

        Todo todo = getTodoById(todoId, userId);

        todoRepository.delete(todo);
    }
}