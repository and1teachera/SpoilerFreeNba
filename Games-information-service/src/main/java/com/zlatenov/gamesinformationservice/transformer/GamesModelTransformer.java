package com.zlatenov.gamesinformationservice.transformer;

import com.zlatenov.gamesinformationservice.model.response.GameResponseModel;
import com.zlatenov.gamesinformationservice.model.response.ScoreResponseModel;
import com.zlatenov.gamesinformationservice.model.service.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.entity.Game;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameInformationDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Score;
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
        return GameServiceModel.builder()
                .startTime(ZonedDateTime.parse(gameResponseModel.getStartTimeUTC()))
                .endTime(StringUtils.isNotEmpty(gameResponseModel.getEndTimeUTC()) ?
                                 ZonedDateTime.parse(gameResponseModel.getEndTimeUTC()) :
                                 null)
                .homeTeamFullName(gameResponseModel.getHTeam().getFullName())
                .awayTeamFullName(gameResponseModel.getVTeam().getFullName())
                .score(Score.builder()
                               .homeTeamPoints(Optional.ofNullable(gameResponseModel.getHTeam().getScore())
                                                       .map(ScoreResponseModel::getPoints)
                                                       .orElse(null))
                               .awayTeamPoints(Optional.ofNullable(gameResponseModel.getVTeam().getScore())
                                                       .map(ScoreResponseModel::getPoints)
                                                       .orElse(null))
                               .build())
                .arena(gameResponseModel.getArena())
                .city(gameResponseModel.getCity())
                .build();
    }

    public List<GameServiceModel> transformResponseToGameServiceModels(List<GameResponseModel> gamesResponseModels) {
        return gamesResponseModels.stream()
                .map(this::transformResponseModelToGameServiceModel)
                .collect(Collectors.toList());
    }

    public GamesDto transformToGamesDto(List<GameServiceModel> gameServiceModels) {
        List<GameDto> gameInformationDtos = gameServiceModels.stream()
                .map(this::transformToGameDto)
                .collect(Collectors.toList());
        return GamesDto.builder().gameDtos(gameInformationDtos).build();
    }

    private GameDto transformToGameDto(GameServiceModel gameServiceModel) {
        return GameDto.builder()
                .homeTeamName(gameServiceModel.getHomeTeamFullName())
                .awayTeamName(gameServiceModel.getAwayTeamFullName())
                .date(gameServiceModel.getStartTime().toString())
                .gameInformationDto(GameInformationDto.builder()
                                            .arena(gameServiceModel.getArena())
                                            .city(gameServiceModel.getCity())
                                            .score(gameServiceModel.getScore())
                                            .startTime(gameServiceModel.getStartTime().toString())
                                            .endTime(Optional.ofNullable(gameServiceModel.getEndTime())
                                                             .map(ZonedDateTime::toString)
                                                             .orElse(null))
                                            .build())
                .build();
    }

    public List<Game> transformToGameEntities(List<GameServiceModel> gameServiceModels) {
        return gameServiceModels.stream().map(this::transformToGameEntity).collect(Collectors.toList());
    }

    private Game transformToGameEntity(GameServiceModel gameServiceModel) {
        Game gameEntity = new Game();
        modelMapper.map(gameServiceModel, gameEntity);
        gameEntity.setHomeTeam(gameServiceModel.getHomeTeamFullName());
        gameEntity.setAwayTeam(gameServiceModel.getAwayTeamFullName());
        gameEntity.setStartTimeUtc(Date.from(
                gameServiceModel.getStartTime().withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId())).toInstant()));
        if (gameServiceModel.getEndTime() != null) {
            gameEntity.setEndTimeUtc(Date.from(
                    gameServiceModel.getEndTime().withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId())).toInstant()));
        }
        return gameEntity;
    }

    public List<GameServiceModel> transformEntitiesToGameServiceModels(List<Game> gameEntities) {
        return gameEntities.stream().map(this::transformEntityToGameServiceModel).collect(Collectors.toList());
    }

    private GameServiceModel transformEntityToGameServiceModel(Game gameEntity) {
        GameServiceModel gameServiceModel = new GameServiceModel();
        modelMapper.map(gameEntity, gameServiceModel);
        gameServiceModel.setStartTime(
                ZonedDateTime.ofInstant(gameEntity.getStartTimeUtc().toInstant(), ZoneId.of(ZoneOffset.UTC.getId())));
        if (!Objects.equals(gameEntity.getEndTimeUtc(), null)) {
            gameServiceModel.setEndTime(
                    ZonedDateTime.ofInstant(gameEntity.getEndTimeUtc().toInstant(), ZoneId.of(ZoneOffset.UTC.getId())));
        }

        gameServiceModel.setScore(Score.builder()
                                          .homeTeamPoints(gameEntity.getHomeTeamScore())
                                          .awayTeamPoints(gameEntity.getAwayTeamScore())
                                          .build());
        gameServiceModel.setHomeTeamFullName(gameEntity.getHomeTeam());
        gameServiceModel.setAwayTeamFullName(gameEntity.getAwayTeam());
        return gameServiceModel;
    }
}
