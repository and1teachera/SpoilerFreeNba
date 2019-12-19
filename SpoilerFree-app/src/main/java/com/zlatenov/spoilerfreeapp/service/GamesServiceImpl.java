package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.service.GameServiceModel;
import com.zlatenov.spoilerfreeapp.model.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class GamesServiceImpl implements GameService {

    private final WebClient.Builder webClientBuilder;
    private final GamesRepository gamesRepository;
    private final TeamRepository teamRepository;
    private final GamesModelTransformer gamesModelTransformer;

    @Override
    public void fetchAllGames() throws UnresponsiveAPIException {
        GamesDto gamesDto = webClientBuilder.build()
                .get()
                .uri("localhost:8083/games")
                .retrieve()
                .bodyToMono(GamesDto.class)
                .block();
        saveGamesInformation(gamesModelTransformer.transformToGamesList(Optional.ofNullable(gamesDto)
                .map(GamesDto::getGameDtos)
                .orElseThrow(
                        UnresponsiveAPIException::new)));
    }

    private void saveGamesInformation(List<Game> games) {
        Map<String, List<Team>> teamToFullName = teamRepository.findAll()
                .stream()
                .collect(groupingBy(Team::getFullName));
        List<Game> gamesToPersist = new ArrayList<>();
        for (Game game : games) {
            String homeTeamFullName = game.getHomeTeam().getFullName();
            String awayTeamFullName = game.getAwayTeam().getFullName();
            if (!teamToFullName.containsKey(homeTeamFullName) ||
                    !teamToFullName.containsKey(awayTeamFullName)) {
                continue;
            }
            game.setHomeTeam(teamToFullName.get(homeTeamFullName).get(0));
            game.setAwayTeam(teamToFullName.get(awayTeamFullName).get(0));
            gamesToPersist.add(game);
        }
        gamesRepository.saveAll(gamesToPersist);
    }

//    @Override
//    public List<GameViewModel> getAllGames() {
//        return gamesModelTransformer.transformToGameViewModels(gamesRepository.findAll());
//    }

    @Override
    public List<GameServiceModel> getGamesForDate(Date date) {
        Date start = Date.from(date.toInstant().minus(2, ChronoUnit.DAYS));
        Date end = Date.from(date.toInstant().plus(3, ChronoUnit.DAYS));
        return gamesModelTransformer.transformToServiceModels(gamesRepository.findAllByStartTimeUtcBetween(start, end));
    }

    @Override
    public List<Date> createDaysNavigationList(Date date) {
        Instant instant = date.toInstant();
        return Arrays.asList(Date.from(instant.minus(2, ChronoUnit.DAYS)),
                             Date.from(instant.minus(1, ChronoUnit.DAYS)), date,
                             Date.from(instant.plus(1, ChronoUnit.DAYS)),
                             Date.from(instant.plus(2, ChronoUnit.DAYS)));
    }

    @Override
    public GameServiceModel getGameInformation(String gameName, String date) {
        String homeTeamShortName = gameName.substring(0, 3);
        String awayTeamShortName = gameName.substring(4, 6);
        Team homeTeam = teamRepository.findByShortName(homeTeamShortName);
        Team awayTeam = teamRepository.findByShortName(awayTeamShortName);
        return gamesModelTransformer.transformToServiceModel(
                gamesRepository.findByHomeTeamAndAwayTeamAndStartTimeUtc(homeTeam, awayTeam,
                        DateUtil.parseDate(date)));
    }

    @Override
    public List<GameServiceModel> getGamesForTeam(String teamName) {
        Team team = teamRepository.findByFullName(teamName);
        return gamesModelTransformer.transformToServiceModels(
                gamesRepository.findByHomeTeamOrAwayTeam(team, team));
    }

    @Override
    public List<GameServiceModel> getAllGames() {
        return gamesModelTransformer.transformToServiceModels(gamesRepository.findAll());
    }


    @Override
    public void fetchGamesForDate(Date date) throws UnresponsiveAPIException {
        GamesDto gamesDto = webClientBuilder.build()
                .post()
                .uri("localhost:8083/games")
                .body(Mono.just(date), Date.class)
                .retrieve()
                .bodyToMono(GamesDto.class)
                .block();
        saveGamesInformation(gamesModelTransformer.transformToGamesList(Optional.ofNullable(gamesDto)
                .map(GamesDto::getGameDtos)
                .orElseThrow(
                        UnresponsiveAPIException::new)));
    }
}
