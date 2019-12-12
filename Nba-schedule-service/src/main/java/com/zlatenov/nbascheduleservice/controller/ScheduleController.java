package com.zlatenov.nbascheduleservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Angel Zlatenov
 */
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    @PostMapping(path = "/login")
    private ResponseEntity login() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/register")
    private ResponseEntity register() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
