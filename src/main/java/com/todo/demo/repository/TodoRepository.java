package com.todo.demo.repository;

import com.todo.demo.model.Todo;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    @Modifying
    @Query("UPDATE todo SET is_done = :done WHERE id = :id")
    public int updateTodo(@Param("id") Long id, @Param("done") Boolean done);

}
