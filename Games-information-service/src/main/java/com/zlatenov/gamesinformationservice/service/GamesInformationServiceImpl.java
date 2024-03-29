package com.zlatenov.gamesinformationservice.service;

import com.zlatenov.gamesinformationservice.model.entity.Game;
import com.zlatenov.gamesinformationservice.model.response.GameResponseModel;
import com.zlatenov.gamesinformationservice.model.response.RapidApiGamesResponse;
import com.zlatenov.gamesinformationservice.model.service.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.transformer.GamesModelTransformer;
import com.zlatenov.gamesinformationservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.gamesinformationservice.repository.GameRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class GamesInformationServiceImpl implements GamesInformationService {

    private final OkHttpClient okHttpClient;
    private final WebClient.Builder webClientBuilder;
    private final ExternalAPIContentProcessor processor;
    private final GameRepository gameRepository;
    private final GamesModelTransformer gamesModelTransformer;

    private List<GameServiceModel> initGames() throws IOException, UnresponsiveAPIException {
        List<GameServiceModel> gameServiceModels = initGamesData();

        return gameServiceModels;
    }

    @Override
    public List<GameServiceModel> getAllGames() {
        return gamesModelTransformer.transformEntitiesToGameServiceModels(gameRepository.findAll());
    }

    @Override
    public List<GameServiceModel> getGamesForDate(Date date) {
        return gamesModelTransformer.transformEntitiesToGameServiceModels(gameRepository.findGamesByStartTimeUtc(date));
    }

    private ResponseBody fetchGamesDataFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder().url("https://api-nba-v1.p.rapidapi.com/games/seasonYear/2019/")
                .get()
                .addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "b87d09763fmsh1444f82d09fff5bp1baa86jsn111f17b325a1")
                .build();

        return Optional.of(okHttpClient.newCall(request).execute())
                .map(Response::body)
                .orElseThrow(UnresponsiveAPIException::new);
    }

    @Override
    public List<GameServiceModel> initGamesData() throws IOException, UnresponsiveAPIException {
        RapidApiGamesResponse rapidApiGamesResponse = processor.processGamesAPIResponse(fetchGamesDataFromExternalAPI());

        List<GameResponseModel> gamesResponseModels = rapidApiGamesResponse.getGames();

        return gamesModelTransformer.transformResponseToGameServiceModels(gamesResponseModels);
    }

    @Override
    public void fetchGamesFromApi() throws IOException, UnresponsiveAPIException {
        List<GameServiceModel> gameServiceModels = initGamesData();

        List<GameServiceModel> gameServiceModelsFromDB = gamesModelTransformer.transformEntitiesToGameServiceModels(
                gameRepository.findAll());

        List<String> teamNames = fetchTeams().stream().map(TeamDto::getFullName).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(gameServiceModelsFromDB)) {
            saveGames(gameServiceModels, teamNames);
            return;
        }

        if (gameServiceModels.containsAll(gameServiceModelsFromDB)
                && gameServiceModels.size() == gameServiceModelsFromDB.size()) {
            return;
        }
        Collection<GameServiceModel> commonElements = CollectionUtils.intersection(gameServiceModelsFromDB,
                gameServiceModels);

        gameServiceModelsFromDB.removeAll(commonElements);
        gameRepository.deleteAll(gamesModelTransformer.transformToGameEntities(gameServiceModelsFromDB));
        gameServiceModels.removeAll(commonElements);
        saveGames(gameServiceModels, teamNames);
    }

    private List<TeamDto> fetchTeams() throws UnresponsiveAPIException {
        TeamsDto teamsDto = webClientBuilder.build()
                .get()
                .uri("localhost:8087/teams")
                .retrieve()
                .bodyToMono(TeamsDto.class)
                .block();
        return Optional.ofNullable(teamsDto).map(TeamsDto::getTeamDtos).orElseThrow(UnresponsiveAPIException::new);
    }

    private void saveGames(List<GameServiceModel> gameServiceModels, List<String> teamNames) {
        List<Game> games = gamesModelTransformer.transformToGameEntities(gameServiceModels);
        gameRepository.saveAll(games.stream()
                .filter(game -> teamNames.contains(game.getHomeTeam()) && teamNames.contains(game.getAwayTeam()))
                .collect(Collectors.toList()));
    }
}
