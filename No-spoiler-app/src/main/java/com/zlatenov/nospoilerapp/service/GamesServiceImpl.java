package com.zlatenov.nospoilerapp.service;

import com.zlatenov.nospoilerapp.model.entity.Game;
import com.zlatenov.nospoilerapp.model.entity.Team;
import com.zlatenov.nospoilerapp.model.view.GameViewModel;
import com.zlatenov.nospoilerapp.repository.GamesRepository;
import com.zlatenov.nospoilerapp.repository.TeamRepository;
import com.zlatenov.nospoilerapp.transformer.GamesModelTransformer;
import com.zlatenov.nospoilersportsapi.model.dto.GamesDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void fetchAllGames() {
        GamesDto gameInformationDto = webClientBuilder.build()
                .get()
                .uri("localhost:8083/games")
                .retrieve()
                .bodyToMono(GamesDto.class)
                .block();
        saveGamesInformation(gameInformationDto);
    }

    private void saveGamesInformation(GamesDto gameInformationDto) {
        List<Game> games = gamesModelTransformer.transformToGamesList(gameInformationDto);

        Map<String, List<Team>> teamToFullName = teamRepository.findAll().stream().collect(groupingBy(Team::getFullName));
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
