package com.trustyourfeet.overhang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trustyourfeet.overhang.util.ResponseHandler;

@RestController
public class FallbackController {

    @GetMapping
    @RequestMapping("/authFallback")
    public ResponseEntity<Object> authFallback() {
        return ResponseHandler.generateResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Auth Service is currently unavailable.",
                null,
                null);
    }

    @GetMapping
    @RequestMapping("/identityFallback")
    public ResponseEntity<Object> identityFallback() {
        return ResponseHandler.generateResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Identity Service is currently unavailable.",
                null,
                null);
    }
}
