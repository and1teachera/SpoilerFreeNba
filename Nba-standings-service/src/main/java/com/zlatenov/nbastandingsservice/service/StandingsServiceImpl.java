package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.entity.Standings;
import com.zlatenov.nbastandingsservice.model.response.RapidApiStandingsResponse;
import com.zlatenov.nbastandingsservice.model.response.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.response.TeamResponseModel;
import com.zlatenov.nbastandingsservice.model.service.Game;
import com.zlatenov.nbastandingsservice.model.service.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.service.Team;
import com.zlatenov.nbastandingsservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.nbastandingsservice.repository.StandingsRepository;
import com.zlatenov.nbastandingsservice.transformer.GamesTransformer;
import com.zlatenov.nbastandingsservice.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.Score;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class StandingsServiceImpl implements StandingsService {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final OkHttpClient client;
    private final WebClient.Builder webClientBuilder;
    private final ExternalAPIContentProcessor processor;
    private final StandingsModelTransformer standingsModelTransformer;
    private final StandingsRepository standingsRepository;
    private final GamesTransformer gamesTransformer;

    @Override
    public void initializeDatabase() throws IOException, UnresponsiveAPIException {
        List<StandingsServiceModel> standingsServiceModels = initStandingsData();

        List<StandingsServiceModel> teamServiceModelsFromDB = standingsModelTransformer.transformEntitiesToStandingsServiceModels(
                standingsRepository.findAll());

        if (CollectionUtils.isEmpty(teamServiceModelsFromDB)) {
            saveStandings(standingsServiceModels);
            return;
        }
//
//        if (standingsServiceModels.containsAll(teamServiceModelsFromDB)
//                && standingsServiceModels.size() == teamServiceModelsFromDB.size()) {
//            return;
//        }
//        Collection<StandingsServiceModel> commonElements = CollectionUtils.intersection(teamServiceModelsFromDB,
//                                                                                        standingsServiceModels);
//
//        teamServiceModelsFromDB.removeAll(commonElements);
//        standingsRepository.deleteAll(standingsModelTransformer.transformToStandingsEntities(teamServiceModelsFromDB));
//        standingsServiceModels.removeAll(commonElements);
//        saveStandings(standingsServiceModels);
    }

    private void saveStandings(List<StandingsServiceModel> standingsServiceModels) {
        standingsRepository.saveAll(standingsModelTransformer.transformToStandingsEntities(standingsServiceModels));
    }

    public ResponseBody fetchStandingsDataFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder().url("https://api-nba-v1.p.rapidapi.com/standings/standard/2019")
                .get()
                .addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "b87d09763fmsh1444f82d09fff5bp1baa86jsn111f17b325a1")
                .build();

        return Optional.of(client.newCall(request).execute())
                .map(Response::body)
                .orElseThrow(UnresponsiveAPIException::new);
    }

    @Override
    public List<StandingsServiceModel> initStandingsData() throws IOException, UnresponsiveAPIException {
        RapidApiStandingsResponse rapidApiTeamsResponse = processor.processStandingsAPIResponse(
                fetchStandingsDataFromExternalAPI());

        List<StandingsResponseModel> standingsResponseModels = rapidApiTeamsResponse.getStandings();
        Map<String, List<TeamResponseModel>> teamIdsMap = fetchTeamNamesFromExternalAPI().stream()
                .collect(groupingBy(TeamResponseModel::getTeamId));

        standingsResponseModels.
                forEach(standingsResponseModel -> standingsResponseModel.setName(
                        teamIdsMap.get(standingsResponseModel.getTeamId()).get(0).getFullName()));

        return standingsModelTransformer.transformResponseToTeamServiceModels(standingsResponseModels);
    }

    @Override
    public void calculateStandings() throws ParseException, UnresponsiveAPIException {
        List<TeamDto> teamDtos = fetchTeamsFromExternalApi();
        List<GameDto> gameDtos = fetchGamesFromExternalApi();

        Map<Date, List<Game>> dateListMap = sortGamesByDate(gamesTransformer.transformToGame(gameDtos));
        List<String> teamNames = teamDtos.stream().map(TeamDto::getFullName).collect(Collectors.toList());
//        Map<String, List<Standings>> teamStandings = new HashMap<>();
//        for (String teamName : teamNames) {
//            teamStandings.put(teamName, new ArrayList<>());
//        }

        List<Standings> standings = new ArrayList<>();

        Map<Date, Map<String, Standings>> standingsMap = new HashMap<>();

        for (Map.Entry<Date, List<Game>> dateListEntry : dateListMap.entrySet()) {
            List<Game> gamesOnDate = dateListEntry.getValue();
            Date date = dateListEntry.getKey();
            standings.addAll(gamesOnDate.stream()
                    .flatMap(game -> createStandingsForTeams(standings, game).stream())
                    .collect(Collectors.toList()));

            //
//            for (String teamName : teamNames) {
//                calculateTeamStandings(gamesOnDate, teamStandings.get(teamName));
//                Map<String, Standings> teamStandingsForDay = new HashMap<>();
//                teamStandingsForDay.put(teamName, teamStandings.get(teamName).get(teamStandings.get(teamName).size() - 1));
//                standingsMap.put(date, teamStandingsForDay);
//            }
        }




        System.out.println(" ");
    }

    private List<TeamResponseModel> fetchTeamNamesFromExternalAPI() throws IOException, UnresponsiveAPIException {
        Request request = new Request.Builder().url("https://api-nba-v1.p.rapidapi.com/teams/league/standard")
                .get()
                .addHeader("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "b87d09763fmsh1444f82d09fff5bp1baa86jsn111f17b325a1")
                .build();

        return processor.processTeamsAPIResponse(Optional.of(client.newCall(request).execute())
                                                         .map(Response::body)
                                                         .orElseThrow(UnresponsiveAPIException::new));
    }

    private Optional<Standings> getLastStanding(String teamName, List<Standings> standings) {
        return standings.stream()
                .filter(standing -> standing.getTeamName().equalsIgnoreCase(teamName))
                .max(Comparator.comparing(Standings::getDate));
    }

    private List<Standings> createStandingsForTeams(List<Standings> standings, Game game) {
//        game.get

        //boolean areFromSameConference = homeTeam.ge

//        List<Standings> homeTeamStandings = standings.stream()
//                .filter(standing -> standing.getTeamName().equalsIgnoreCase(homeTeam.getFullName()))
//                .collect(Collectors.toList());
//
//        List<Standings> awayTeamStandings = standings.stream()
//                .filter(standing -> standing.getTeamName().equalsIgnoreCase(awayTeam.getFullName()))
//                .collect(Collectors.toList());
//
//        String winnersName = determineWinnersName(game);
//        if (homeTeam.getFullName().equalsIgnoreCase(winnersName)){
//            Optional<Standings> lastStanding = getLastStanding(winnersName, standings);
//            if (lastStanding.isPresent()) {
//
//            }
//        }
        return null;
    }

    private void calculateTeamStandings(List<Game> gamesOnDate, List<Standings> standings)
            throws ParseException {
        Standings lastStanding = standings.get(standings.size() - 1);
        String teamName = lastStanding.getTeamName();
        Optional<Game> gameForTeam = gamesOnDate.stream()
                .filter(game -> game.getHomeTeam().getName().equalsIgnoreCase(teamName)
                        || game.getAwayTeam().getName().equalsIgnoreCase(teamName))
                .findFirst();
        if(gameForTeam.isPresent()) {
            Game game = gameForTeam.get();
            String winnersName = determineWinnersName(game);
        }
        else {
            standings.add(Standings.builder().teamName(teamName).date(gamesOnDate.get(0).getDate())
                                  .build());
        }
    }

    private String determineWinnersName(Game game) {
        Score score = game.getScore();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        if(score.getAwayTeamPoints() > score.getHomeTeamPoints()){
            return awayTeam.getName();
        }
        return homeTeam.getName();
    }

    private Map<Date, List<Game>> sortGamesByDate(List<Game> games) throws ParseException {
        Date today = Date.from(Instant.now());
        Map<Date, List<Game>> gamesToDate = new TreeMap<>();
        for (Game game : games) {
            Score score = game.getScore();
            if (game.getDate().before(today) && (score.getHomeTeamPoints() != null
                            && score.getAwayTeamPoints() != null)) {
                if(!gamesToDate.containsKey(game.getDate())) {
                    gamesToDate.put(game.getDate(), new ArrayList<>());
                }
                gamesToDate.get(game.getDate()).add(game);
            }
        }
        return gamesToDate;
    }

    private List<GameDto> fetchGamesFromExternalApi() throws UnresponsiveAPIException {
        GamesDto gamesDto = webClientBuilder.build()
                .get()
                .uri("localhost:8083/games")
                .retrieve()
                .bodyToMono(GamesDto.class)
                .block();
        return Optional.ofNullable(gamesDto).map(GamesDto::getGameDtos).orElseThrow(UnresponsiveAPIException::new);
    }

    private List<TeamDto> fetchTeamsFromExternalApi() throws UnresponsiveAPIException {
        TeamsDto teamsDto = webClientBuilder.build()
                .get()
                .uri("localhost:8087/teams")
                .retrieve()
                .bodyToMono(TeamsDto.class)
                .block();

        return Optional.ofNullable(teamsDto).map(TeamsDto::getTeamDtos).orElseThrow(UnresponsiveAPIException::new);
    }
}
