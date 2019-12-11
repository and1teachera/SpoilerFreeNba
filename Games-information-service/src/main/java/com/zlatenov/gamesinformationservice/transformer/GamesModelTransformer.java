package com.zlatenov.gamesinformationservice.transformer;

import com.zlatenov.gamesinformationservice.model.GameResponseModel;
import com.zlatenov.gamesinformationservice.model.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.Score;
import com.zlatenov.gamesinformationservice.model.entity.Game;
import com.zlatenov.gamesinformationservice.model.entity.Team;
import com.zlatenov.spoilerfreesportsapi.model.dto.GameInformationDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.TeamScoreDto;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class GamesModelTransformer {

    private final ModelMapper modelMapper;

    private GameServiceModel transformResponseModelToGameServiceModel(GameResponseModel gameResponseModel) {
        GameServiceModel gameServiceModel = GameServiceModel.builder()
                .startTime(ZonedDateTime.parse(gameResponseModel.getStartTimeUTC()))
                .endTime(StringUtils.isNotEmpty(gameResponseModel.getEndTimeUTC()) ?
                                 ZonedDateTime.parse(gameResponseModel.getEndTimeUTC()) :
                                 null)
                .homeTeamFullName(gameResponseModel.getHTeam().getFullName())
                .awayTeamFullName(gameResponseModel.getVTeam().getFullName())
                .homeTeamScore(
                        Optional.ofNullable(gameResponseModel.getHTeam().getScore()).map(Score::getPoints).orElse(null))
                .awayTeamScore(
                        Optional.ofNullable(gameResponseModel.getVTeam().getScore()).map(Score::getPoints).orElse(null))
                .build();

        modelMapper.map(gameResponseModel, gameServiceModel);

        return gameServiceModel;
    }

    public List<GameServiceModel> transformResponseToGameServiceModels(List<GameResponseModel> gamesResponseModels) {
        return gamesResponseModels.stream()
                .map(this::transformResponseModelToGameServiceModel)
                .collect(Collectors.toList());
    }

    public GamesDto transformToGamesDto(List<GameServiceModel> gameServiceModels) {
        List<GameInformationDto> gameInformationDtos = gameServiceModels.stream()
                .map(this::transformToGameInformationDto)
                .collect(Collectors.toList());
        return new GamesDto(gameInformationDtos);
    }

    private GameInformationDto transformToGameInformationDto(GameServiceModel gameServiceModel) {
        return GameInformationDto.builder()
                .arena(gameServiceModel.getArena())
                .startTime(gameServiceModel.getStartTime().toString())
                .endTime(gameServiceModel.getEndTime() != null ? gameServiceModel.getEndTime().toString() : null)
                .homeTeam(TeamScoreDto.builder()
                                  .fullName(gameServiceModel.getHomeTeamFullName())
                                  .points(gameServiceModel.getHomeTeamScore())
                                  .build())
                .awayTeam(TeamScoreDto.builder()
                                  .fullName(gameServiceModel.getAwayTeamFullName())
                                  .points(gameServiceModel.getAwayTeamScore())
                                  .build())
                .build();
    }

    public List<Game> transformToGameEntities(List<GameServiceModel> gameServiceModels) {
        return gameServiceModels.stream()
                .map(this::transformToGameEntity)
                .collect(Collectors.toList());
    }

    private Game transformToGameEntity(GameServiceModel gameServiceModel) {
        Game gameEntity = new Game();
        modelMapper.map(gameServiceModel, gameEntity);
//        teamsModelTransformer.transformToTeamEntity(gameServiceModel.getHomeTeamServiceModel())
        gameEntity.setHomeTeam(Team.builder().fullName(gameServiceModel.getHomeTeamFullName()).build());
        gameEntity.setAwayTeam(Team.builder().fullName(gameServiceModel.getAwayTeamFullName()).build());
        gameEntity.setStartTimeUtc(Date.from(
                gameServiceModel.getStartTime()
                        .withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId()))
                        .toInstant()));
        if(gameServiceModel.getEndTime() != null) {
            gameEntity.setEndTimeUtc(Date.from(
                    gameServiceModel.getEndTime().withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId())).toInstant()));
        }
        return gameEntity;
    }

    public List<GameServiceModel> transformEntitiesToGameServiceModels(List<Game> gameEntities) {
        return gameEntities.stream()
                .map(this::transformEntityToGameServiceModel)
                .collect(Collectors.toList());
    }

    private GameServiceModel transformEntityToGameServiceModel(Game gameEntity) {
        GameServiceModel gameServiceModel = new GameServiceModel();
        modelMapper.map(gameEntity, gameServiceModel);
        gameServiceModel.setStartTime(ZonedDateTime.ofInstant(gameEntity.getStartTimeUtc().toInstant(),
                                                              ZoneId.of(ZoneOffset.UTC.getId())));
        if (!Objects.equals(gameEntity.getEndTimeUtc(), null)) {
            gameServiceModel.setEndTime(
                    ZonedDateTime.ofInstant(gameEntity.getEndTimeUtc().toInstant(), ZoneId.of(ZoneOffset.UTC.getId())));
        }

        gameServiceModel.setHomeTeamFullName(gameEntity.getHomeTeam().getFullName());
        gameServiceModel.setAwayTeamFullName(gameEntity.getAwayTeam().getFullName());
        return gameServiceModel;
    }
}
