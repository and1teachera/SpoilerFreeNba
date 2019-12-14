package com.zlatenov.teamsinformationservice.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import com.zlatenov.teamsinformationservice.model.response.TeamResponseModel;
import com.zlatenov.teamsinformationservice.model.service.TeamServiceModel;
import com.zlatenov.teamsinformationservice.model.entity.Team;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Component
@AllArgsConstructor
public class TeamsModelTransformer {

    private final ModelMapper modelMapper;

    public TeamServiceModel transformResponseToTeamServiceModel(TeamResponseModel hTeam) {
        return TeamServiceModel.builder()
                .fullName(hTeam.getFullName())
                .nickName(hTeam.getNickname())
                .shortName(hTeam.getShortName())
                .city(hTeam.getCity())
                .logo(hTeam.getLogo())
                .confName(hTeam.getLeagues().getLeagues().get(0).getConfName())
                .divName(hTeam.getLeagues().getLeagues().get(0).getDivName())
                .build();
    }

    public Team transformToTeamEntity(TeamServiceModel teamServiceModel) {
        Team teamEntity = new Team();
        modelMapper.map(teamServiceModel, teamEntity);
        return teamEntity;
    }

    public TeamServiceModel transformEntityToTeamServiceModel(Team teamEntity) {
        TeamServiceModel teamServiceModel = new TeamServiceModel();
        modelMapper.map(teamEntity, teamServiceModel);
        return teamServiceModel;
    }

    public List<TeamServiceModel> transformEntitiesToTeamServiceModels(List<Team> teams) {
        return teams.stream()
                .map(this::transformEntityToTeamServiceModel)
                .collect(Collectors.toList());
    }

    public List<TeamServiceModel> transformResponseToTeamServiceModels(List<TeamResponseModel> teamResponseModels) {
        return teamResponseModels.stream()
                .filter(teamResponseModel -> Objects.equals(teamResponseModel.getNbaFranchise(), 1))
                .map(this::transformResponseToTeamServiceModel).collect(Collectors.toList());
    }

    public List<Team> transformToTeamEntities(List<TeamServiceModel> teamServiceModelsFromDB) {
        return teamServiceModelsFromDB.stream()
                .map(this::transformToTeamEntity)
                .collect(Collectors.toList());
    }

    public TeamsDto transformToTeamsDto(List<TeamServiceModel> teamServiceModels) {
        List<TeamDto> gameInformationDtos = teamServiceModels.stream()
                .map(this::transformToTeamDto)
                .collect(Collectors.toList());
        return new TeamsDto(gameInformationDtos);
    }

    private TeamDto transformToTeamDto(TeamServiceModel teamServiceModel) {
        return TeamDto.builder()
                .fullName(teamServiceModel.getFullName())
                .city(teamServiceModel.getCity())
                .shortName(teamServiceModel.getShortName())
                .nickname(teamServiceModel.getNickName())
                .confName(teamServiceModel.getConfName())
                .divName(teamServiceModel.getDivName())
                .logo(teamServiceModel.getLogo())
                .build();
    }
}
