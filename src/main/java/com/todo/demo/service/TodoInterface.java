package com.todo.demo.service;

import com.todo.demo.model.Todo;

public interface TodoInterface {

     Todo findById(Long id);

     Iterable<Todo> findAllTodosByUserId(String email);

     Iterable<Todo> findAll();

     Todo save(String title, String description, String email);

     Long deleteById(Long id);

     Todo complete(Long id, Boolean done);

}
