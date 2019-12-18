package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Standings;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.StandingsServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.StandingsRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.standings.StandingsDtos;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Angel Zlatenov
 */

@Service
@AllArgsConstructor
public class StandingsServiceImpl implements StandingsService {

    private StandingsRepository standingsRepository;
    private StandingsModelTransformer standingsModelTransformer;
    private TeamRepository teamRepository;
    private final WebClient.Builder webClientBuilder;
    private GamesRepository gamesRepository;

    @Override
    public void fetchCurrentStandings() throws UnresponsiveAPIException {
        StandingsDtos standingsDtos = webClientBuilder.build()
                .get()
                .uri("localhost:8086/standings/current")
                .retrieve()
                .bodyToMono(StandingsDtos.class)
                .block();
        saveStandings(standingsModelTransformer.transformToStandings(Optional.ofNullable(standingsDtos)
                .map(StandingsDtos::getStandingsDtos)
                .orElseThrow(
                        UnresponsiveAPIException::new)));
    }

    private void saveStandings(List<Standings> standings) {
        standingsRepository.saveAll(standings);
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
