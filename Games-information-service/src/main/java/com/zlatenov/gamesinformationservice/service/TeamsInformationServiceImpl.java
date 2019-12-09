package com.zlatenov.gamesinformationservice.service;

import com.zlatenov.gamesinformationservice.model.TeamServiceModel;
import com.zlatenov.gamesinformationservice.repository.TeamRepository;
import com.zlatenov.gamesinformationservice.transformer.TeamsModelTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Service
@AllArgsConstructor
public class TeamsInformationServiceImpl implements TeamsInformationService {

    private TeamRepository teamRepository;
    private TeamsModelTransformer teamsModelTransformer;

    @Override
    public List<TeamServiceModel> getAllTeams() {
        return teamsModelTransformer.transformEntitiesToTeamServiceModels(teamRepository.findAll());
    }
}
