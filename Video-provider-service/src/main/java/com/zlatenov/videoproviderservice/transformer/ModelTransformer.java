package com.zlatenov.videoproviderservice.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.videoproviderservice.model.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class ModelTransformer {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Game transformDtoToGame(GameDto gameDto) throws ParseException {
        return Game.builder()
                .homeTeamName(gameDto.getHomeTeamName())
                .awayTeamName(gameDto.getAwayTeamName())
                .date(DATE_FORMAT.parse(gameDto.getDate()))
                .score(gameDto.getGameInformationDto().getScore())
                .build();
    }
}
