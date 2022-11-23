package com.cognizant.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.auth.entity.Signin;
import com.cognizant.auth.exception.JwtAuthenticationException;
import com.cognizant.auth.repository.SigninRepository;
import com.cognizant.auth.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private SigninRepository signinRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> signin(Signin signin) {
        log.info("Signing in with username {}", signin.getUsername());
        Optional<Signin> signinEntity = signinRepository.findByUsernameAndPassword(signin.getUsername(), signin.getPassword());
        if (signinEntity.isPresent()) {
            log.info("Signing in success with username {}", signin.getUsername());
            Signin temp = signinEntity.get();

            return Map.of("name", temp.getUsername(), "role", temp.getRole(), "token", jwtUtil.generateToken(temp));
        }

        log.info("Signing in failed with username {}", signin.getUsername());
        throw new JwtAuthenticationException("Username or password is incorrect");
    }

    public Map<String, Object> signup(Signin signin) {
        log.info("Signing up with username {}", signin.getUsername());
        signin = signinRepository.save(signin);

        return Map.of("id", signin.getId(), "username", signin.getUsername());
    }
}
