package com.zlatenov.videoproviderservice.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import com.zlatenov.videoproviderservice.model.Game;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class ModelTransformer {

    private final ModelMapper modelMapper;

    public Game transformDtoToGame(GameDto gameDto) {
        return Game.builder()
                .homeTeamName(gameDto.getHomeTeamName())
                .awayTeamName(gameDto.getAwayTeamName())
                .date(DateUtil.parseDate(gameDto.getDate()))
                .score(gameDto.getGameInformationDto().getScore())
                .build();
    }
}
