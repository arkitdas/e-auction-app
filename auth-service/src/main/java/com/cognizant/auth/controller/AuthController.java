package com.cognizant.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.auth.entity.Signin;
import com.cognizant.auth.payload.ApiResponse;
import com.cognizant.auth.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Signin signin) {
    	return new ResponseEntity<>(ApiResponse.ofSuccess(201,authService.signin(signin), "User added successfully") , HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Signin signin) {
    	return new ResponseEntity<>(ApiResponse.ofSuccess(201,authService.signup(signin), "User added successfully") , HttpStatus.CREATED);
    }
}
