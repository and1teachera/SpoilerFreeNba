package com.zlatenov.teamsinformationservice.model.transformer;

import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import com.zlatenov.teamsinformationservice.model.response.PlayerResponseModel;
import com.zlatenov.teamsinformationservice.model.response.TeamResponseModel;
import com.zlatenov.teamsinformationservice.model.service.PlayerServiceModel;
import com.zlatenov.teamsinformationservice.model.service.TeamServiceModel;
import com.zlatenov.teamsinformationservice.model.entity.Team;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final PlayersModelTransformer playersModelTransformer;

    public TeamServiceModel transformResponseToTeamServiceModel(TeamResponseModel teamResponseModel) {
        return TeamServiceModel.builder()
                .id(teamResponseModel.getTeamId())
                .fullName(teamResponseModel.getFullName())
                .nickName(teamResponseModel.getNickname())
                .shortName(teamResponseModel.getShortName())
                .city(teamResponseModel.getCity())
                .logo(teamResponseModel.getLogo())
                .confName(teamResponseModel.getLeagues().getLeagues().get(0).getConfName())
                .divName(teamResponseModel.getLeagues().getLeagues().get(0).getDivName())
                .build();
    }

    public Team transformToTeamEntity(TeamServiceModel teamServiceModel) {
        return Team.builder()
                .fullName(teamServiceModel.getFullName())
                .city(teamServiceModel.getCity())
                .shortName(teamServiceModel.getShortName())
                .confName(teamServiceModel.getConfName())
                .divName(teamServiceModel.getDivName())
                .nickName(teamServiceModel.getNickName())
                .logo(teamServiceModel.getLogo())
                .players(teamServiceModel.getPlayers().stream()
                        .map(playersModelTransformer::transformToPlayer).collect(Collectors.toList()))
                .build();
    }

    public TeamServiceModel transformEntityToTeamServiceModel(Team team) {
        return TeamServiceModel.builder()
                .fullName(team.getFullName())
                .city(team.getCity())
                .shortName(team.getShortName())
                .confName(team.getConfName())
                .divName(team.getDivName())
                .nickName(team.getNickName())
                .logo(team.getLogo())
                .players(team.getPlayers().stream()
                        .map(playersModelTransformer::transformToServiceModel).collect(Collectors.toList()))
                .build();
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
                .playerDtos(playersModelTransformer.transformToPlayerDtos(teamServiceModel.getPlayers()))
                .logo(teamServiceModel.getLogo())
                .build();
    }

    public List<PlayerServiceModel> transformResponseToPlayerServiceModels(List<PlayerResponseModel> players) {
        return players.stream()
                .map(this::transformResponseToPlayerServiceModel)
                .collect(Collectors.toList());
    }

    private PlayerServiceModel transformResponseToPlayerServiceModel(PlayerResponseModel playerResponseModel) {
        return PlayerServiceModel.builder()
                .firstName(playerResponseModel.getFirstName())
                .lastName(playerResponseModel.getLastName())
                .collegeName(playerResponseModel.getCollegeName())
                .country(playerResponseModel.getCountry())
                .dateOfBirth(StringUtils.isNotEmpty(playerResponseModel.getDateOfBirth()) ?
                        DateUtil.parseDate(playerResponseModel.getDateOfBirth()) :
                        null)
                .heightInMeters(StringUtils.isNotEmpty(playerResponseModel.getHeightInMeters()) ?
                        Float.valueOf(playerResponseModel.getHeightInMeters()) :
                        null)
                .weightInKilograms(StringUtils.isNotEmpty(playerResponseModel.getWeightInKilograms()) ?
                        Float.valueOf(playerResponseModel.getWeightInKilograms()) :
                        null)
                .jersey(StringUtils.isNotEmpty(playerResponseModel.getLeagues().getLeagues().get(0).getJersey()) ?
                        Integer.valueOf(playerResponseModel.getLeagues().getLeagues().get(0).getJersey()) :
                        null)
                .position(playerResponseModel.getLeagues().getLeagues().get(0).getPos())
                .startNba(playerResponseModel.getStartNba())
                .teamId(StringUtils.isNotEmpty(playerResponseModel.getTeamId()) ?
                        playerResponseModel.getTeamId() :
                        null)
                .yearsPro(playerResponseModel.getYearsPro())
                .isActive(StringUtils.isNotEmpty(playerResponseModel.getLeagues().getLeagues().get(0).getActive())
                        && playerResponseModel.getLeagues().getLeagues().get(0).getActive().equals("1"))
                .build();
    }
}
