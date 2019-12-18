package com.zlatenov.spoilerfreeapp.model.transformer;

import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.PlayerServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.TeamServiceModel;
import com.zlatenov.spoilerfreeapp.model.view.PlayersViewModel;
import com.zlatenov.spoilerfreeapp.model.view.TeamViewModel;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@AllArgsConstructor
@Component
public class TeamModelTransformer {

    private final ModelMapper modelMapper;

    public List<Team> transformToTeamsList(TeamsDto teamsDto) {
        return teamsDto.getTeamDtos().stream()
                .map(this::transformToTeam)
                .collect(Collectors.toList());
    }

    public Team transformToTeam(TeamDto teamDto) {
        return Team.builder()
                .fullName(teamDto.getFullName())
                .shortName(teamDto.getShortName())
                .nickName(teamDto.getNickname())
                .city(teamDto.getCity())
                .logo(teamDto.getLogo())
                .confName(teamDto.getConfName())
                .divName(teamDto.getDivName())
                .build();
    }

    public TeamViewModel transformToTeamViewModel(TeamServiceModel teamServiceModel){
        return TeamViewModel.builder()
                .city(teamServiceModel.getCity())
                .fullName(teamServiceModel.getFullName())
                .logo(teamServiceModel.getLogo())
                .shortName(teamServiceModel.getShortName())
                .nickName(teamServiceModel.getNickName())
                .division(teamServiceModel.getDivision())
                .conference(teamServiceModel.getConference())
                .isFavorite(teamServiceModel.isFavorite())
                .isWatched(teamServiceModel.isWatched())
                .build();
    }

    public PlayersViewModel transformToPlayersViewModel(List<PlayerServiceModel> playersByTeamName) {
        return null;
    }

    public TeamServiceModel transformToTeamServiceModel(TeamViewModel team) {
        return TeamServiceModel.builder()
                .city(team.getCity())
                .fullName(team.getFullName())
                .logo(team.getLogo())
                .shortName(team.getShortName())
                .nickName(team.getNickName())
                .division(team.getDivision())
                .conference(team.getConference())
                .isFavorite(team.isFavorite())
                .isWatched(team.isWatched())
                .build();
    }

    public TeamServiceModel transformToTeamServiceModel(Team team) {
        return TeamServiceModel.builder()
                .city(team.getCity())
                .fullName(team.getFullName())
                .logo(team.getLogo())
                .shortName(team.getShortName())
                .nickName(team.getNickName())
                .division(team.getDivName())
                .conference(team.getConfName())
                .build();
    }

    public List<TeamServiceModel> transformToTeamServiceModels(List<Team> teams) {
        return teams.stream().map(this::transformToTeamServiceModel).collect(Collectors.toList());
    }
}
