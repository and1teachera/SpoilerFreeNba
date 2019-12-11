package com.zlatenov.gamesinformationservice.service;

import com.zlatenov.gamesinformationservice.model.GameResponseModel;
import com.zlatenov.gamesinformationservice.model.GameServiceModel;
import com.zlatenov.gamesinformationservice.model.RapidApiGamesResponse;
import com.zlatenov.gamesinformationservice.model.RapidApiTeamsResponse;
import com.zlatenov.gamesinformationservice.model.TeamResponseModel;
import com.zlatenov.gamesinformationservice.model.TeamServiceModel;
import com.zlatenov.gamesinformationservice.model.entity.Game;
import com.zlatenov.gamesinformationservice.model.entity.Team;
import com.zlatenov.gamesinformationservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.gamesinformationservice.repository.GameRepository;
import com.zlatenov.gamesinformationservice.repository.TeamRepository;
import com.zlatenov.gamesinformationservice.transformer.GamesModelTransformer;
import com.zlatenov.gamesinformationservice.transformer.TeamsModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class GamesInformationServiceImpl implements GamesInformationService {

    private final OkHttpClient okHttpClient;
    private final ExternalAPIContentProcessor processor;
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final GamesModelTransformer gamesModelTransformer;
    private final TeamsModelTransformer teamsModelTransformer;

    private List<GameServiceModel> initGames() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> teamServiceModels = initTeamsData();
        List<GameServiceModel> gameServiceModels = initGamesData();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2019-07-01T23:00:00.000Z");
        Date utc = Date.from(zonedDateTime.withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId())).toInstant());

        ZonedDateTime sofia = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Sofia"));
        ZonedDateTime customZone = zonedDateTime.toInstant().atZone(ZoneOffset.UTC);

        return gameServiceModels;
    }

    @Override
    public List<GameServiceModel> getAllGames() {
        return gamesModelTransformer.transformEntitiesToGameServiceModels(gameRepository.findAll());
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

    private ResponseBody fetchTeamDataFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder()
                        .url("https://api-nba-v1.p.rapidapi.com/teams/league/standard")
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
    public List<TeamServiceModel> initTeamsData() throws IOException, UnresponsiveAPIException {
        RapidApiTeamsResponse rapidApiTeamsResponse = processor.processTeamsAPIResponse(fetchTeamDataFromExternalAPI());

        List<TeamResponseModel> teamResponseModels = rapidApiTeamsResponse.getTeams();

        return teamsModelTransformer.transformResponseToTeamServiceModels(
                teamResponseModels);
    }

    @Override
    public void initializeDatabase() throws IOException, UnresponsiveAPIException {
        initializeTeamsInformation();
        initializeGamesInformation();
    }

    private void initializeTeamsInformation() throws IOException, UnresponsiveAPIException {
        List<TeamServiceModel> teamServiceModels = initTeamsData();

        List<TeamServiceModel> teamServiceModelsFromDB = teamsModelTransformer.transformEntitiesToTeamServiceModels(
                teamRepository.findAll());

        if (CollectionUtils.isEmpty(teamServiceModelsFromDB)) {
            saveTeams(teamServiceModels);
            return;
        }

        if (teamServiceModels.containsAll(teamServiceModelsFromDB)
                && teamServiceModels.size() == teamServiceModelsFromDB.size()) {
            return;
        }
        Collection<TeamServiceModel> commonElements = CollectionUtils.intersection(teamServiceModelsFromDB,
                                                                                   teamServiceModels);

        teamServiceModelsFromDB.removeAll(commonElements);
        teamRepository.deleteAll(teamsModelTransformer.transformToTeamEntities(teamServiceModelsFromDB));
        teamServiceModels.removeAll(commonElements);
        saveTeams(teamServiceModels);
    }

    private void saveTeams(List<TeamServiceModel> teamServiceModels) {
        List<Team> teams = teamsModelTransformer.transformToTeamEntities(teamServiceModels);
        teamRepository.saveAll(teams);
    }

    private void initializeGamesInformation() throws IOException, UnresponsiveAPIException {
        List<GameServiceModel> gameServiceModels = initGamesData();

        List<GameServiceModel> gameServiceModelsFromDB = gamesModelTransformer.transformEntitiesToGameServiceModels(
                gameRepository.findAll());

        if (CollectionUtils.isEmpty(gameServiceModelsFromDB)) {
            saveGames(gameServiceModels);
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
        saveGames(gameServiceModels);
    }

    private void saveGames(List<GameServiceModel> gameServiceModels) {
        List<Game> games = gamesModelTransformer.transformToGameEntities(gameServiceModels);
        Map<String, List<Team>> teamToFullName = teamRepository.findAll().stream().collect(groupingBy(Team::getFullName));

        List<Game> gamesToPersist = new ArrayList<>();
        for (Game game : games) {
            String homeTeamFullName = game.getHomeTeam().getFullName();
            String awayTeamFullName = game.getAwayTeam().getFullName();
            if(!teamToFullName.containsKey(homeTeamFullName) || !teamToFullName.containsKey(awayTeamFullName)) {
                continue;
            }
            game.setHomeTeam(teamToFullName.get(homeTeamFullName).get(0));
            game.setAwayTeam(teamToFullName.get(awayTeamFullName).get(0));
            gamesToPersist.add(game);
        }
        gameRepository.saveAll(gamesToPersist);
    }
}
