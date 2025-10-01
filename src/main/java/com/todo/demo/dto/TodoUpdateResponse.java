package com.todo.demo.dto;

import com.todo.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoUpdateResponse {
    private String message;
    private Todo todo;

}
