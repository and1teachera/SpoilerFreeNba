package com.zlatenov.gamesinformationservice.controller;

import com.zlatenov.gamesinformationservice.model.service.GameServiceModel;
import com.zlatenov.gamesinformationservice.service.GamesInformationService;
import com.zlatenov.gamesinformationservice.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@RestController
@AllArgsConstructor
public class GamesInformationController {

    private final GamesInformationService gamesInformationService;
    private final GamesModelTransformer gamesModelTransformer;

    @GetMapping(path = "/games")
    private ResponseEntity games() throws IOException, UnresponsiveAPIException {
        List<GameServiceModel> allGames = gamesInformationService.getAllGames();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2019-10-22T00:00:00.000Z");
        //ZonedDateTime dateTime = ZonedDateTime.ofInstant(new Date("2019-10-23").toInstant(), ZoneId.of("UTC"));
        GamesDto gamesDto = gamesModelTransformer.transformToGamesDto(allGames.stream()
                                                                             .filter(gameServiceModel ->
                                                                                             gameServiceModel.getStartTime().isAfter(
                                                                                                     zonedDateTime))
                                                                             .collect(Collectors.toList()));
        return ResponseEntity
                .ok(gamesDto);
    }

}
