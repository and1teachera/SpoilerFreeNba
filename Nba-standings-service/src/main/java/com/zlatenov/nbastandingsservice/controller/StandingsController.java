package com.zlatenov.nbastandingsservice.controller;

import com.zlatenov.nbastandingsservice.service.StandingsService;
import com.zlatenov.nospoilersportsapi.model.exception.UnresponsiveAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@RestController
@RequiredArgsConstructor
public class StandingsController {

    private StandingsService standingsService;

    @PostMapping(path = "/standings")
    private ResponseEntity login() throws IOException, UnresponsiveAPIException {
        //standingsService.fetchStandings();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
