package com.todo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table
@NoArgsConstructor
public class Todo {

    @Id
    private Long id;
    private String title;
    private String description;
    private boolean isDone;
    private Long user_id;



    public Todo (String title, String description) {
        this.title = title;
        this.description = description;
        this.isDone = false;
    }
}
