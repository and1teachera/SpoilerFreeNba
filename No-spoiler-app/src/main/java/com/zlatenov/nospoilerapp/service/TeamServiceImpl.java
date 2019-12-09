package com.zlatenov.nospoilerapp.service;

import com.zlatenov.nospoilerapp.model.entity.Team;
import com.zlatenov.nospoilerapp.model.view.TeamViewModel;
import com.zlatenov.nospoilerapp.repository.TeamRepository;
import com.zlatenov.nospoilerapp.transformer.TeamModelTransformer;
import com.zlatenov.nospoilersportsapi.model.dto.TeamsDto;
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
