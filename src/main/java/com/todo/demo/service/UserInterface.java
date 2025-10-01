package com.todo.demo.service;

import com.todo.demo.dto.JWTAuthenticationDTO;
import com.todo.demo.dto.RefreshTokenDTO;
import com.todo.demo.dto.UserCredentialsDTO;
import com.todo.demo.dto.UserDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.AuthenticationException;

public interface UserInterface {
    JWTAuthenticationDTO login(UserCredentialsDTO userCredentialsDTO)  throws AuthenticationException
            ;
    JWTAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
    UserDTO getUserById(Long id) throws ChangeSetPersister.NotFoundException;
    UserDTO getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;
    String register(UserDTO userDTO);
}
