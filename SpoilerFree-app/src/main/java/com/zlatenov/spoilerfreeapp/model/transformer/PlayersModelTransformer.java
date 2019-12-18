package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Player;
import com.zlatenov.spoilerfreeapp.model.service.PlayerServiceModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class PlayersModelTransformer {

    public List<PlayerServiceModel> transformToServiceModels(List<Player> players) {
        return players.stream().map(this::transformToServiceModel).collect(Collectors.toList());
    }

    private PlayerServiceModel transformToServiceModel(Player player) {
        return PlayerServiceModel.builder()
                .name(player.getName())
                .build();
    }
}
