package com.zlatenov.teamsinformationservice.controller;

import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.teamsinformationservice.model.service.TeamServiceModel;
import com.zlatenov.teamsinformationservice.service.TeamsInformationService;
import com.zlatenov.teamsinformationservice.model.transformer.TeamsModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */
@RestController
@AllArgsConstructor
public class TeamsInformationController {

    private final TeamsInformationService teamsInformationService;
    private final TeamsModelTransformer teamsModelTransformer;


    @GetMapping(path = "/teams")
    private ResponseEntity teams() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> allTeams = teamsInformationService.getAllTeams();
        TeamsDto teamsDto = teamsModelTransformer.transformToTeamsDto(allTeams);

        return ResponseEntity
                .ok(teamsDto);

    }

}
