package com.todo.demo.controller;

import com.todo.demo.dto.*;
import com.todo.demo.model.Todo;
import com.todo.demo.service.TodoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("/api")
public class TodoController {
    @Autowired
    private TodoInterface todoService;

    @GetMapping("/hello")
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
    public ResponseEntity<Iterable<Todo>> getTodos() {
        Iterable<Todo> todo = todoService.findAll();
        return ResponseEntity.ok(todo);
    }

    @PostMapping("/todo")
    public ResponseEntity<? >saveTodo(@RequestBody TodoCreateRequest req){
        if (req == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Todo should not be empty"));
        }
        if (req.getTitle() == null || req.getTitle().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Title should not be empty"));
        }
        todoService.save(req.getTitle(), req.getDescription());
        return ResponseEntity.ok(new TodoCreateResponse("Successfully created", req));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id){
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
    public ResponseEntity<?> updateTodo(@PathVariable("id") Long id, @RequestBody TodoUpdateRequest req){
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
