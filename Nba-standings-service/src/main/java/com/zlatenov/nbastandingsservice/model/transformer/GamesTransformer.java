package com.zlatenov.nbastandingsservice.model.transformer;

import com.zlatenov.nbastandingsservice.model.service.Game;
import com.zlatenov.nbastandingsservice.model.service.GameInformation;
import com.zlatenov.nbastandingsservice.model.service.Team;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameInformationDto;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class GamesTransformer {

    private final ModelMapper modelMapper;

    public List<Game> transformDtoListToGames(List<GameDto> gameDtos, List<Team> teams) {
        List<Game> games = gameDtos.stream()
                .map(this::transformDtoToGame)
                .collect(Collectors.toList());

        Set<Team> teamsSet = games.stream()
                .flatMap(game -> Stream.of(game.getHomeTeam(), game.getAwayTeam()))
                .collect(Collectors.toSet());

        Map<String, List<Team>> teamsMap = teams.stream().collect(groupingBy(Team::getName));

        games.forEach(game -> game.setHomeTeam(teamsMap.get(game.getHomeTeam().getName()).get(0)));
        games.forEach(game -> game.setAwayTeam(teamsMap.get(game.getAwayTeam().getName()).get(0)));

        return games;
    }

    private void populateTeamsData(Game game, List<Team> teams) {
        game.getHomeTeam();
        game.getAwayTeam();
    }

    private Game transformDtoToGame(GameDto gameDto) {
        return Game.builder()
                .homeTeam(Team.builder().name(gameDto.getHomeTeamName()).build())
                .awayTeam(Team.builder().name(gameDto.getAwayTeamName()).build())
                .date(DateUtil.parseDate(gameDto.getDate()))
                .gameInformation(transformDtoToGameInformation(gameDto.getGameInformationDto()))
                .score(gameDto.getGameInformationDto().getScore())
                .build();
    }

    private GameInformation transformDtoToGameInformation(GameInformationDto gameInformationDto) {
        GameInformation gameInformation = GameInformation.builder().build();
        modelMapper.map(gameInformationDto, gameInformation);
        return gameInformation;
    }
}
