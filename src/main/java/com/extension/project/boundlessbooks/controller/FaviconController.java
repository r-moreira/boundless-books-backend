package com.extension.project.boundlessbooks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @RequestMapping("favicon.ico")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleFavicon() {
        // No content needed, just return 404
    }
}