package com.zlatenov.gamesinformationservice.controller;

import com.zlatenov.gamesinformationservice.model.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.TeamServiceModel;
import com.zlatenov.gamesinformationservice.service.GamesInformationServiceImpl;
import com.zlatenov.gamesinformationservice.service.TeamsInformationService;
import com.zlatenov.gamesinformationservice.transformer.GamesModelTransformer;
import com.zlatenov.gamesinformationservice.transformer.TeamsModelTransformer;
import com.zlatenov.nospoilersportsapi.model.dto.GamesDto;
import com.zlatenov.nospoilersportsapi.model.dto.TeamsDto;
import com.zlatenov.nospoilersportsapi.model.exception.UnresponsiveAPIException;
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

    private final GamesInformationServiceImpl gamesInformationService;
    private final GamesModelTransformer gamesModelTransformer;
    private final TeamsInformationService teamsInformationService;
    private final TeamsModelTransformer teamsModelTransformer;

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

        //.stream().limit(500).collect(Collectors.toList())
//        Flux<GameInformationDto> gameInformationDtoFlux = Flux.fromIterable(gamesDto.getGameInformationDtos())
//                .publishOn(Schedulers.parallel());

//        gameInformationDtoFlux.publish();
        return ResponseEntity
                .ok(gamesDto);
    }



    @GetMapping(path = "/teams")
    private ResponseEntity teams() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> allGames = teamsInformationService.getAllTeams();
        TeamsDto gamesDto = teamsModelTransformer.transformToTeamsDto(allGames);

        return ResponseEntity
                .ok(gamesDto);

    }

}
