package com.extension.project.boundlessbooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    @GetMapping
    public List<ResponseEntity<String>> getAllUsers() {
        return Collections.singletonList(ResponseEntity.ok().body("Hello"));
    }

    @GetMapping("/me")
    public String getCurrentUser() {
        return "Hello";
    }
}
