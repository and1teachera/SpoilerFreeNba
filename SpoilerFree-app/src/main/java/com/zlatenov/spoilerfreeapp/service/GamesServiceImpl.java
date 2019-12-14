package com.zlatenov.spoilerfreeapp.service;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Team;
import com.zlatenov.spoilerfreeapp.model.view.GameViewModel;
import com.zlatenov.spoilerfreeapp.repository.GamesRepository;
import com.zlatenov.spoilerfreeapp.repository.TeamRepository;
import com.zlatenov.spoilerfreeapp.transformer.GamesModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        try {
            saveGamesInformation(gamesModelTransformer.transformToGamesList(Optional.ofNullable(gamesDto)
                                                                                    .map(GamesDto::getGameDtos)
                                                                                    .orElseThrow(
                                                                                            UnresponsiveAPIException::new)));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveGamesInformation(List<Game> games) throws ParseException {
        Map<String, List<Team>> teamToFullName = teamRepository.findAll()
                .stream()
                .collect(groupingBy(Team::getFullName));
        List<Game> gamesToPersist = new ArrayList<>();
        for (Game game : games) {
            String homeTeamFullName = game.getHomeTeam().getFullName();
            String awayTeamFullName = game.getAwayTeam().getFullName();
            if (!teamToFullName.containsKey(homeTeamFullName) || !teamToFullName.containsKey(awayTeamFullName)) {
                continue;
            }
            game.setHomeTeam(teamToFullName.get(homeTeamFullName).get(0));
            game.setAwayTeam(teamToFullName.get(awayTeamFullName).get(0));
            gamesToPersist.add(game);
        }
        gamesRepository.saveAll(gamesToPersist);
    }

    @Override
    public List<GameViewModel> getAllGames() {
        return gamesModelTransformer.transformToGameViewModel(gamesRepository.findAll());
    }

}
