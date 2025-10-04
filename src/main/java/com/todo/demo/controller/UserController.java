package com.todo.demo.controller;

import com.todo.demo.dto.UserDTO;
import com.todo.demo.service.Impl.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserImpl userImpl;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDto){
        System.out.println(userDto);
        return userImpl.register(userDto);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id)throws ChangeSetPersister.NotFoundException {
        return userImpl.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email)throws ChangeSetPersister.NotFoundException {
        return userImpl.getUserByEmail(email);
    }
}
