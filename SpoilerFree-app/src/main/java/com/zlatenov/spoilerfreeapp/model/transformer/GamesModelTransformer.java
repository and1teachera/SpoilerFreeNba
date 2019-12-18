package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Score;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class GamesModelTransformer {

    private final TeamModelTransformer teamModelTransformer;

    public List<GameViewModel> transformToGameViewModels(List<GameServiceModel> games) {
        return games.stream().map(this::transformToGameViewModel).collect(Collectors.toList());
    }

    public GameViewModel transformToGameViewModel(GameServiceModel game) {
        return GameViewModel.builder()
                .homeTeam(teamModelTransformer.transformToTeamViewModel(game.getHomeTeam()))
                .awayTeam(teamModelTransformer.transformToTeamViewModel(game.getAwayTeam()))
                .homeTeamPoints(game.getScore().getHomeTeamPoints())
                .awayTeamPoints(game.getScore().getAwayTeamPoints())
                .date(game.getDate().toString())
                .build();
    }

    public List<Game> transformToGamesList(List<GameDto> gamesDto) {
        return gamesDto.stream().map(this::transformToGame).collect(Collectors.toList());
    }

    public Game transformToGame(GameDto gameDto) {
        return Game.builder()
                .arena(gameDto.getGameInformationDto().getArena())
                .city(gameDto.getGameInformationDto().getCity())
                .startTimeUtc(DateUtil.parseDate(gameDto.getGameInformationDto().getStartTime()))
                .endTimeUtc(gameDto.getGameInformationDto().getEndTime() != null ?
                        DateUtil.parseDate(gameDto.getGameInformationDto().getEndTime()) :
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

    public GameServiceModel transformToServiceModel(GameViewModel game) {
        return GameServiceModel.builder()
                .homeTeam(teamModelTransformer.transformToTeamServiceModel(game.getHomeTeam()))
                .awayTeam(teamModelTransformer.transformToTeamServiceModel(game.getAwayTeam()))
                .score(Score.builder()
                        .homeTeamPoints(game.getHomeTeamPoints())
                        .awayTeamPoints(game.getAwayTeamPoints())
                        .build())
                .date(DateUtil.parseDate(game.getDate()))
                .build();
    }

    public List<GameServiceModel> transformToServiceModels(List<Game> games) {
        return games.stream().map(this::transformToServiceModel).collect(Collectors.toList());
    }

    public GameServiceModel transformToServiceModel(Game game) {
        return GameServiceModel.builder()
                .homeTeam(teamModelTransformer.transformToTeamServiceModel(game.getHomeTeam()))
                .awayTeam(teamModelTransformer.transformToTeamServiceModel(game.getAwayTeam()))
                .score(Score.builder()
                        .homeTeamPoints(game.getHomeTeamScore())
                        .awayTeamPoints(game.getAwayTeamScore())
                        .build())
                .date(game.getStartTimeUtc())
                .build();
    }

    public List<GameDto> transformToGameDtos(List<Game> games) {
        return games.stream().map(this::transformToGameDto).collect(Collectors.toList());
    }

    public GameDto transformToGameDto(Game game) {
        return GameDto.builder()
                .homeTeamName(game.getHomeTeam().getFullName())
                .awayTeamName(game.getAwayTeam().getFullName())
                .date(game.getStartTimeUtc().toString())
                .build();
    }
}
