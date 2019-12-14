package com.zlatenov.nbastandingsservice.controller;

import com.zlatenov.nbastandingsservice.model.service.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.service.StandingsService;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */
@RestController
@RequiredArgsConstructor
public class StandingsController {

    private StandingsService standingsService;

    @PostMapping(path = "/standings/current")
    private ResponseEntity standings() throws IOException, UnresponsiveAPIException {
        //standingsService.fetchStandings();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(path = "/standings")
    private ResponseEntity standingsOnDate(@RequestBody Date date) throws IOException, UnresponsiveAPIException {
        //standingsService.fetchStandings();
        List<StandingsServiceModel> standingsForDate = standingsService.getStandingsForDate(date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
