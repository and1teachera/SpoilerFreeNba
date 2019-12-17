package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.PlayerServiceModel;
import com.zlatenov.spoilerfreeapp.model.service.TeamServiceModel;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.transformer.TeamModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final WebClient.Builder webClientBuilder;
    private final TeamRepository teamRepository;
    private final TeamModelTransformer teamModelTransformer;

    @Override
    public void fetchAllTeams() {
        TeamsDto teamsDto = webClientBuilder.build()
                .get()
                .uri("localhost:8087/teams")
                .retrieve()
                .bodyToMono(TeamsDto.class)
                .block();
        saveTeamsInformation(teamsDto);
    }

    @Override
    public List<TeamServiceModel> getAllTeams() {
        return null;
    }

    @Override
    public List<PlayerServiceModel> getPlayersByTeamName(String teamName) {
        return null;
    }

    private void saveTeamsInformation(TeamsDto teamsDto) {
        List<Team> teams = teamModelTransformer.transformToTeamsList(teamsDto);
        teamRepository.saveAll(teams);
    }
//
//    @Override
//    public List<Team> getAllTeams() {
//        return teamRepository.findAll();
//    }

//    public List<Player> getPlayersForTeam(TeamServiceModel teamServiceModel) {
//        return getPlayersByTeamName(teamServiceModel.getFullName());
//    }

//    @Override
//    public List<Player> getPlayersByTeamName(String teamName) {
//        return null;
//    }

    @Override
    public void addRemoveFromWatched(String teamName) {

    }

    @Override
    public void addRemoveFromFavorite(String teamName) {

    }
}
