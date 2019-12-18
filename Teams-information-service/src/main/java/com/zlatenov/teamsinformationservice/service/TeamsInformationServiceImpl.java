package com.zlatenov.teamsinformationservice.service;

import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.teamsinformationservice.model.entity.Team;
import com.zlatenov.teamsinformationservice.model.response.RapidApiPlayersResponseModel;
import com.zlatenov.teamsinformationservice.model.response.RapidApiTeamsResponseModel;
import com.zlatenov.teamsinformationservice.model.response.TeamResponseModel;
import com.zlatenov.teamsinformationservice.model.service.PlayerServiceModel;
import com.zlatenov.teamsinformationservice.model.service.TeamServiceModel;
import com.zlatenov.teamsinformationservice.model.transformer.TeamsModelTransformer;
import com.zlatenov.teamsinformationservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.teamsinformationservice.repository.TeamRepository;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */

@Service
@AllArgsConstructor
public class TeamsInformationServiceImpl implements TeamsInformationService {


    private final OkHttpClient okHttpClient;
    private final TeamRepository teamRepository;
    private final TeamsModelTransformer teamsModelTransformer;
    private final ExternalAPIContentProcessor processor;

    @Override
    public List<TeamServiceModel> getAllTeams() {
        return teamsModelTransformer.transformEntitiesToTeamServiceModels(teamRepository.findAll());
    }

    @Override
    public List<TeamServiceModel> initTeamsData() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> teamServiceModels = fetchTeamServiceModels();
        List<PlayerServiceModel> playerServiceModels = fetchPlayerServiceModels().stream()
                .filter(PlayerServiceModel::isActive)
                .filter(playerServiceModel -> StringUtils.isNotEmpty(playerServiceModel.getTeamId()))
                .collect(Collectors.toList());

        Map<String, List<TeamServiceModel>> teamServiceModelToId = teamServiceModels.stream().collect(groupingBy(TeamServiceModel::getId));
        Map<String, List<PlayerServiceModel>> playerServiceModelsToTeamId = playerServiceModels.stream().collect(
                groupingBy(PlayerServiceModel::getTeamId));

        teamServiceModelToId.keySet().forEach(
                teamId -> teamServiceModelToId.get(teamId).get(0).setPlayers(playerServiceModelsToTeamId.get(teamId)));

        return teamServiceModels;
    }

    private List<PlayerServiceModel> fetchPlayerServiceModels() throws IOException, UnresponsiveAPIException {
        RapidApiPlayersResponseModel rapidApiPlayersResponse = processor.processPlayersAPIResponse(fetchPlayersDataFromExternalAPI());

        return teamsModelTransformer.transformResponseToPlayerServiceModels(
                rapidApiPlayersResponse.getPlayers());
    }

    private List<TeamServiceModel> fetchTeamServiceModels() throws IOException, UnresponsiveAPIException {
        RapidApiTeamsResponseModel rapidApiTeamsResponse = processor.processTeamsAPIResponse(fetchTeamDataFromExternalAPI());
        List<TeamResponseModel> teamResponseModels = rapidApiTeamsResponse.getTeams();

        return teamsModelTransformer.transformResponseToTeamServiceModels(
                teamResponseModels);
    }

    @Override
    public void initializeTeamsDatabase() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> teamServiceModels = initTeamsData();

        List<TeamServiceModel> teamServiceModelsFromDB = teamsModelTransformer.transformEntitiesToTeamServiceModels(
                teamRepository.findAll());

        if (CollectionUtils.isEmpty(teamServiceModelsFromDB)) {
            saveTeams(teamServiceModels);
            return;
        }

        if (teamServiceModels.containsAll(teamServiceModelsFromDB)
                && teamServiceModels.size() == teamServiceModelsFromDB.size()) {
            return;
        }
        Collection<TeamServiceModel> commonElements = CollectionUtils.intersection(teamServiceModelsFromDB,
                                                                                   teamServiceModels);

        teamServiceModelsFromDB.removeAll(commonElements);
        teamRepository.deleteAll(teamsModelTransformer.transformToTeamEntities(teamServiceModelsFromDB));
        teamServiceModels.removeAll(commonElements);
        saveTeams(teamServiceModels);
    }

    private void saveTeams(List<TeamServiceModel> teamServiceModels) {
        List<Team> teams = teamsModelTransformer.transformToTeamEntities(teamServiceModels);
        teams.forEach(team -> team.getPlayers().forEach(player -> player.setTeam(team)));
        teamRepository.saveAll(teams);
    }


    private ResponseBody fetchTeamDataFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder()
                .url("https://api-nba-v1.p.rapidapi.com/teams/league/standard")
                .get()
                .addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "b87d09763fmsh1444f82d09fff5bp1baa86jsn111f17b325a1")
                .build();

        return Optional.of(okHttpClient.newCall(request).execute())
                .map(Response::body)
                .orElseThrow(UnresponsiveAPIException::new);
    }

    private ResponseBody fetchPlayersDataFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder()
                .url("https://api-nba-v1.p.rapidapi.com/players/league/standard")
                .get()
                .addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "b87d09763fmsh1444f82d09fff5bp1baa86jsn111f17b325a1")
                .build();

        return Optional.of(okHttpClient.newCall(request).execute())
                .map(Response::body)
                .orElseThrow(UnresponsiveAPIException::new);
    }

}
