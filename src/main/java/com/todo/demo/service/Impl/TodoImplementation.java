package com.todo.demo.service.Impl;

import com.todo.demo.model.Todo;
import com.todo.demo.repository.TodoRepository;
import com.todo.demo.service.TodoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoImplementation implements TodoInterface {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo save(String title, String description) {
        Todo todo = new Todo(title, description);
        todo.setUser_id(1L);
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
