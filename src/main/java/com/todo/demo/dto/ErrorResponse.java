package com.todo.demo.dto;

import com.todo.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
