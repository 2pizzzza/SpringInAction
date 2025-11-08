package com.todo.demo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long id;
    private Long fromUser;
    private Long toUser;
}
