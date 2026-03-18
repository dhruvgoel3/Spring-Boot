package com.practiceee.practicee.dtos;

import com.practiceee.practicee.entitiy.Todo;
import com.practiceee.practicee.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoMapper {
    @Autowired
    private final ModelMapper modelMapper;

    public Todo dtoToEntity(TodoRequestDTO todoRequestDTO) {
        Todo todo = this.modelMapper.map(todoRequestDTO, Todo.class);
        return todo;

    }

    public TodoResponseDTO dtoToEntity(Todo todo) {
        TodoResponseDTO dto = this.modelMapper.map(todo, TodoResponseDTO.class);
        return dto;

    }


}
