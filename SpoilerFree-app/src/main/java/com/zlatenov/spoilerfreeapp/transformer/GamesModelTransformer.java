package com.zlatenov.spoilerfreeapp.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.GameInformationDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.GamesDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Component
public class GamesModelTransformer {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<GameViewModel> transformToGameViewModel(List<Game> entities) {
        return Collections.singletonList(new GameViewModel());
    }

    public List<Game> transformToGamesList(GamesDto gamesDto)  {
        return gamesDto.getGameInformationDtos().stream()
                .map(gameInformationDto -> {
                    try {
                        return transformToGame(gameInformationDto);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return new Game();
                })
                .collect(Collectors.toList());
    }

    private Game transformToGame(GameInformationDto gameInformationDto) throws ParseException {
        return Game.builder().arena(gameInformationDto.getArena())
                .startTimeUtc(simpleDateFormat.parse(gameInformationDto.getStartTime()))
                .endTimeUtc(gameInformationDto.getEndTime() != null ?
                                    simpleDateFormat.parse(gameInformationDto.getEndTime())
                                    : null)
                .homeTeam(Team.builder().fullName(gameInformationDto.getHomeTeam().getFullName()).build())
                .homeTeamScore(gameInformationDto.getHomeTeam().getPoints())
                .awayTeam(Team.builder().fullName(gameInformationDto.getAwayTeam().getFullName()).build())
                .awayTeamScore(gameInformationDto.getAwayTeam().getPoints())
                .build();
    }

}
