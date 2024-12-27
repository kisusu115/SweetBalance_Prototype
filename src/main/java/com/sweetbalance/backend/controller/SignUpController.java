package com.sweetbalance.backend.controller;

import com.sweetbalance.backend.dto.SignUpDTO;
import com.sweetbalance.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/api/sign-up")
    public void signUp(@RequestBody SignUpDTO signUpDTO){
        userService.join(signUpDTO);
    }
}
