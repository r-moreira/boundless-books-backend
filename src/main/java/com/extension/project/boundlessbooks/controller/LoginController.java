package com.extension.project.boundlessbooks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
//@RestController
//@RequestMapping("/api/v1/oauth2")
//public class LoginController {
//
//    @GetMapping("/google/redirect")
//    public String grantCode(
//        @RequestParam("code") String code,
//        @RequestParam("scope") String scope,
//        @RequestParam("authuser") String authUser,
//        @RequestParam("prompt") String prompt
//    ) {
//        log.info("Received code: {}", code);
//
//        return "Received code: " + code;
//    }
//
//}