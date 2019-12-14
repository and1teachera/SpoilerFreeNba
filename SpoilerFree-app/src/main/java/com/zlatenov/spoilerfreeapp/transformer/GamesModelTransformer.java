package com.zlatenov.spoilerfreeapp.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.Score;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Angel Zlatenov
 */
@Component
public class GamesModelTransformer {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<GameViewModel> transformToGameViewModel(List<Game> entities) {
        return Collections.singletonList(new GameViewModel());
    }

    public List<Game> transformToGamesList(List<GameDto> gamesDto) throws ParseException {
        List<Game> games = new ArrayList<>();
        for (GameDto gameDto : gamesDto) {
            games.add(transformToGame(gameDto));
        }
        return games;
    }

    private Game transformToGame(GameDto gameDto) throws ParseException {
        return Game.builder()
                .arena(gameDto.getGameInformationDto().getArena())
                .startTimeUtc(simpleDateFormat.parse(gameDto.getGameInformationDto().getStartTime()))
                .endTimeUtc(gameDto.getGameInformationDto().getEndTime() != null ?
                                    simpleDateFormat.parse(gameDto.getGameInformationDto().getEndTime()) :
                                    null)
                .homeTeam(Team.builder().fullName(gameDto.getHomeTeamName()).build())
                .homeTeamScore(Optional.ofNullable(gameDto.getGameInformationDto().getScore())
                                       .map(Score::getHomeTeamPoints)
                                       .orElse(null))
                .awayTeam(Team.builder().fullName(gameDto.getAwayTeamName()).build())
                .awayTeamScore(Optional.ofNullable(gameDto.getGameInformationDto().getScore())
                                       .map(Score::getAwayTeamPoints)
                                       .orElse(null))
                .build();
    }

}
