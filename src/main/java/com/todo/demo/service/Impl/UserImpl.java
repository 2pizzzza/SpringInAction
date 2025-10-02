package com.todo.demo.service.Impl;

import com.todo.demo.dto.JWTAuthenticationDTO;
import com.todo.demo.dto.RefreshTokenDTO;
import com.todo.demo.dto.UserCredentialsDTO;
import com.todo.demo.dto.UserDTO;
import com.todo.demo.mapper.UserMapper;
import com.todo.demo.model.User;
import com.todo.demo.repository.UserRepository;
import com.todo.demo.security.jwt.JwtService;
import com.todo.demo.service.UserInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserInterface {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final static Logger logger = LoggerFactory.getLogger(UserImpl.class);


    @Override
    public JWTAuthenticationDTO login(UserCredentialsDTO userCredentialsDTO) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDTO);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JWTAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception{
        String refreshToken = refreshTokenDTO.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromJwtToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw  new AuthenticationServiceException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    @Transactional
    public UserDTO getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public String register(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    private User findByCredentials(UserCredentialsDTO userCredentialsDTO) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDTO.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw  new AuthenticationServiceException("Email or password is incorrect");
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }

}
