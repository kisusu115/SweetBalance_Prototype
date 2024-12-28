package com.sweetbalance.backend.controller;

import com.sweetbalance.backend.dto.SignUpDTO;
import com.sweetbalance.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO){
        try {
            userService.join(signUpDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
