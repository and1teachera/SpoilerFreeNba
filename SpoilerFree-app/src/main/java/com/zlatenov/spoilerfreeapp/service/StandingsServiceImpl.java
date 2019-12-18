package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.StandingsRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public class StandingsServiceImpl implements StandingsService {

    private StandingsRepository standingsRepository;
    private StandingsModelTransformer standingsModelTransformer;
    private TeamRepository teamRepository;
    private GamesRepository gamesRepository;

    @Override
    public void fetchAllStandings() throws UnresponsiveAPIException {

    }

    @Override
    public List<StandingsServiceModel> getStandingsForDate(Date date) {
        return standingsModelTransformer.transformToServiceModels(standingsRepository.findByDate(date));
    }

    @Override
    public List<StandingsServiceModel> getStandingsInformation(String gameName, String date) {
        String homeTeamShortName = gameName.substring(0, 3);
        String awayTeamShortName = gameName.substring(4, 6);
        Team homeTeam = teamRepository.findByShortName(homeTeamShortName);
        Team awayTeam = teamRepository.findByShortName(awayTeamShortName);
        return standingsModelTransformer.transformToServiceModels(
                Arrays.asList(standingsRepository.findByTeamAndDate(homeTeam, DateUtil.parseDate(date)),
                        standingsRepository.findByTeamAndDate(awayTeam, DateUtil.parseDate(date))));
    }

    @Override
    public List<StandingsServiceModel> getCurrentStandings() {
        return standingsModelTransformer.transformToServiceModels(standingsRepository.findByDate(DateUtil.getCurrentDateWithoutTime()));
    }

    @Override
    public StandingsServiceModel getStandingsForTeam(String teamName) {
        return standingsModelTransformer.transformToServiceModel(
                standingsRepository.findByTeamAndDate(
                        teamRepository.findByFullName(teamName),
                        DateUtil.getCurrentDateWithoutTime()));
    }
}
