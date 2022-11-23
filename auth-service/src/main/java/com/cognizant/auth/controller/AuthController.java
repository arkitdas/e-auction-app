package com.cognizant.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.auth.entity.Signin;
import com.cognizant.auth.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public Map<String, Object> signin(@RequestBody Signin signin) {
        return authService.signin(signin);
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody Signin signin) {
        return authService.signup(signin);
    }
}
