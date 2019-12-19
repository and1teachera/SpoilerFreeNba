package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Player;
import com.zlatenov.spoilerfreeapp.model.service.PlayerServiceModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.PlayerDto;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
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
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .collegeName(player.getCollegeName())
                .country(player.getCountry())
                .dateOfBirth(player.getDateOfBirth())
                .position(player.getPosition())
                .startNba(player.getStartNba())
                .jersey(player.getJersey())
                .heightInMeters(player.getHeightInMeters())
                .weightInKilograms(player.getWeightInKilograms())
                .yearsPro(player.getYearsPro())
                .build();
    }

    public List<Player> transformToPlayers(List<PlayerDto> playerDtos) {
        return playerDtos.stream().map(this::transformToPlayer).collect(Collectors.toList());
    }

    public Player transformToPlayer(PlayerDto playerDto) {
        return Player.builder()
                .firstName(playerDto.getFirstName())
                .lastName(playerDto.getLastName())
                .collegeName(playerDto.getCollegeName())
                .country(playerDto.getCountry())
                .dateOfBirth(playerDto.getDateOfBirth() != null ? DateUtil.parseDate(playerDto.getDateOfBirth()): null)
                .position(playerDto.getPosition())
                .startNba(playerDto.getStartNba())
                .jersey(playerDto.getJersey())
                .heightInMeters(playerDto.getHeightInMeters())
                .weightInKilograms(playerDto.getWeightInKilograms())
                .yearsPro(playerDto.getYearsPro())
                .build();
    }
}
