package com.todo.demo.service;

import com.todo.demo.model.Todo;

public interface TodoInterface {

    public Todo findById(Long id);

    public Iterable<Todo> findAll();

    public Todo save(String title, String description);

    public Long deleteById(Long id);

    public Todo complete(Long id, Boolean done);

}
