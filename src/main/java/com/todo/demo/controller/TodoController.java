package com.todo.demo.controller;

import com.todo.demo.dto.*;
import com.todo.demo.model.Todo;
import com.todo.demo.security.CustomUserDetails;
import com.todo.demo.service.TodoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TodoController {
    @Autowired
    private TodoInterface todoService;

    private final static Logger logger = LoggerFactory.getLogger(TodoController.class);

    @GetMapping("/")
    public String sayHello() {
        return "<h1>Hello world</h1>";
    }

    @GetMapping( "/todo/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") Long id) {
        Todo todo = todoService.findById(id);
        if (todo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new  ErrorResponse("todo not found"));
        }

        return ResponseEntity.ok(todo);
    }

    @GetMapping( "/todos")
    public ResponseEntity<Iterable<Todo>> getTodos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Iterable<Todo> todo = todoService.findAll();
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/todos/auth")
    public ResponseEntity<Iterable<Todo>> getTodosByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String email = userDetails.getUsername();
        Iterable<Todo> todos = todoService.findAllTodosByUserId(email);
        return ResponseEntity.ok(todos);
    }

    @PostMapping("/todo")
    public ResponseEntity<?>saveTodo(
            @RequestBody TodoCreateRequest req,
            Authentication authentication) {
        if (req == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Todo should not be empty"));
        }
        if (req.getTitle() == null || req.getTitle().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Title should not be empty"));
        }

        String email = authentication.getName();
        logger.info(email);
        todoService.save(req.getTitle(), req.getDescription(), email);
        return ResponseEntity.ok(new TodoCreateResponse("Successfully created", req));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id,
                                        Authentication authentication){
        if (id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Id should not be empty"));
        }
        Todo todo  = todoService.findById(id);
        if (todo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Todo not found"));
        }

        todoService.deleteById(id);
        return ResponseEntity.ok(new TodoDeleteResponse("Successfully deleted", id));
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") Long id, @RequestBody TodoUpdateRequest req,
                                        Authentication authentication){
        if (id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Id should not be empty"));
        }
        if (req == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Request should not be empty"));
        }
        if (todoService.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not found"));
        }

        Todo todo = todoService.complete(id, req.getIsDone());
        return ResponseEntity.ok(new TodoUpdateResponse("Successfully updated", todo));
    }

}
