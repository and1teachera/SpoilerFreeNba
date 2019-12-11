package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.view.TeamViewModel;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.transformer.TeamModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.TeamsDto;
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
                .uri("localhost:8083/teams")
                .retrieve()
                .bodyToMono(TeamsDto.class)
                .block();
        saveTeamsInformation(teamsDto);
    }

    private void saveTeamsInformation(TeamsDto teamsDto) {
        List<Team> teams = teamModelTransformer.transformToTeamsList(teamsDto);
        teamRepository.saveAll(teams);
    }

    @Override
    public List<TeamViewModel> getAllTeams() {
        return null;
    }
}
