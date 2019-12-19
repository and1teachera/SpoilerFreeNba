package com.zlatenov.nbastandingsservice.controller;

import com.zlatenov.nbastandingsservice.model.transformer.StandingsModelTransformer;
import com.zlatenov.nbastandingsservice.service.StandingsService;
import com.zlatenov.spoilerfreesportsapi.model.dto.standings.StandingsDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.standings.StandingsDtos;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@AllArgsConstructor
public class StandingsController {

    private StandingsService standingsService;
    private StandingsModelTransformer standingsModelTransformer;

    @GetMapping(path = "/standings/current")
    private ResponseEntity standings() throws IOException, UnresponsiveAPIException {
        List<StandingsDto> standingsDtos =
                standingsModelTransformer.transformToStandingsDtos(
                        standingsService.getStandingsForDate(
                                DateUtil.getCurrentDateWithoutTime()));
        return ResponseEntity
                .ok(StandingsDtos.builder()
                        .standingsDtos(standingsDtos)
                        .build());
    }

    @PostMapping(path = "/standings")
    private ResponseEntity standingsOnDate(@RequestBody Date date) throws IOException, UnresponsiveAPIException {
        List<StandingsDto> standingsDtos = standingsModelTransformer.transformToStandingsDtos(standingsService.getStandingsForDate(date));
        return ResponseEntity
                .ok(StandingsDtos.builder()
                        .standingsDtos(standingsDtos)
                        .build());
    }
}
