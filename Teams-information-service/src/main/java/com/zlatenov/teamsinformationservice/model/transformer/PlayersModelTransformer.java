package com.zlatenov.teamsinformationservice.model.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.team.PlayerDto;
import com.zlatenov.teamsinformationservice.model.entity.Player;
import com.zlatenov.teamsinformationservice.model.service.PlayerServiceModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class PlayersModelTransformer {

    public PlayerServiceModel transformToServiceModel(Player player) {
        return PlayerServiceModel.builder()
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .yearsPro(player.getYearsPro())
                .startNba(player.getStartNba())
                .position(player.getPosition())
                .country(player.getCountry())
                .collegeName(player.getCollegeName())
                .dateOfBirth(player.getDateOfBirth())
                .heightInMeters(player.getHeightInMeters())
                .weightInKilograms(player.getWeightInKilograms())
                .jersey(player.getJersey())
                .isActive(player.isActive())
                .build();
    }

    public Player transformToPlayer(PlayerServiceModel playerServiceModel) {
        return Player.builder()
                .firstName(playerServiceModel.getFirstName())
                .lastName(playerServiceModel.getLastName())
                .yearsPro(playerServiceModel.getYearsPro())
                .startNba(playerServiceModel.getStartNba())
                .position(playerServiceModel.getPosition())
                .country(playerServiceModel.getCountry())
                .collegeName(playerServiceModel.getCollegeName())
                .dateOfBirth(playerServiceModel.getDateOfBirth())
                .heightInMeters(playerServiceModel.getHeightInMeters())
                .weightInKilograms(playerServiceModel.getWeightInKilograms())
                .jersey(playerServiceModel.getJersey())
                .isActive(playerServiceModel.isActive())
                .build();
    }

    public List<PlayerDto> transformToPlayerDtos(List<PlayerServiceModel> players) {
        return players.stream().map(this::transformToPlayerDto).collect(Collectors.toList());
    }

    private PlayerDto transformToPlayerDto(PlayerServiceModel playerServiceModel) {
        return PlayerDto.builder()
                .firstName(playerServiceModel.getFirstName())
                .lastName(playerServiceModel.getLastName())
                .yearsPro(playerServiceModel.getYearsPro())
                .startNba(playerServiceModel.getStartNba())
                .position(playerServiceModel.getPosition())
                .country(playerServiceModel.getCountry())
                .collegeName(playerServiceModel.getCollegeName())
                .dateOfBirth(playerServiceModel.getDateOfBirth() != null ? playerServiceModel.getDateOfBirth().toString() : null)
                .heightInMeters(playerServiceModel.getHeightInMeters())
                .weightInKilograms(playerServiceModel.getWeightInKilograms())
                .jersey(playerServiceModel.getJersey())
                .build();
    }
}
