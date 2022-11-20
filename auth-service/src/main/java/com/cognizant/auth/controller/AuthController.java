package com.eauction.authservice.controller;

import com.eauction.authservice.entity.Signin;
import com.eauction.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
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
