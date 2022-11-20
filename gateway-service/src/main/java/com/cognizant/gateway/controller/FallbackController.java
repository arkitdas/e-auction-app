package com.cognizant.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<String> fallback() {
        log.error("Service is down,please try after sometime");
        return new ResponseEntity<String>("Service is down,please try after sometime", HttpStatus.GATEWAY_TIMEOUT);
    }
}
