package com.todo.demo.repository;

import com.todo.demo.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT email, password FROM users WHERE email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT id FROM users WHERE email = :email")
    Long findIdByEmail(@Param("email") String email);
}
