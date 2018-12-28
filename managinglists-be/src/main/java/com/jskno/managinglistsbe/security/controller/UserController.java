package com.jskno.managinglistsbe.security.controller;

import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

}
