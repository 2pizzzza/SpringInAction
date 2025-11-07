package com.todo.demo.service.Impl;

import com.todo.demo.controller.TodoController;
import com.todo.demo.model.Todo;
import com.todo.demo.repository.TodoRepository;
import com.todo.demo.repository.UserRepository;
import com.todo.demo.service.TodoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoImpl implements TodoInterface {

    private final static Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Todo> findAllTodosByUserId(String email){
        Long userId = userRepository.findIdByEmail(email);
        return todoRepository.findTodosByUserId(userId);
    }

    @Override
    public Iterable<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo save(String title, String description, String email) {
        Long id = userRepository.findIdByEmail(email);
        Todo todo = new Todo(title, description);
        todo.setUserId(id);
        return todoRepository.save(todo);
    }

    @Override
    public Long deleteById(Long id) {
        todoRepository.deleteById(id);
        return id;
    }

    @Override
    public Todo complete(Long id, Boolean done) {
        todoRepository.updateTodo(id, done);
        return todoRepository.findById(id).orElse(null);
    }
}
