package com.extension.project.boundlessbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @Operation(hidden = true)
    @RequestMapping("favicon.ico")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleFavicon() {
    }
}