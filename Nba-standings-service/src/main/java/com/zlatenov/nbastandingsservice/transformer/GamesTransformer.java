package com.zlatenov.nbastandingsservice.transformer;

import com.zlatenov.nbastandingsservice.model.service.Game;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class GamesTransformer {

    public List<Game> transformToGame(List<GameDto> gameDtos) {
        return new ArrayList<>();
    }
}
