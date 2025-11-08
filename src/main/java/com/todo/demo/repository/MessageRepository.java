package com.todo.demo.repository;

import com.todo.demo.model.Message;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT * FROM messages WHERE from_user = :from_user AND to_user = :to_user")
    Iterable<Message> findMessagesByUser(@Param("from_user") String fromUser, @Param("to_user") String toUser);
}
