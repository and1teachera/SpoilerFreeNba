package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.RapidApiStandingsResponse;
import com.zlatenov.nbastandingsservice.model.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.TeamResponseModel;
import com.zlatenov.nbastandingsservice.model.entity.Standings;
import com.zlatenov.nbastandingsservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.nbastandingsservice.repository.StandingsRepository;
import com.zlatenov.nbastandingsservice.transformer.GamesTransformer;
import com.zlatenov.nbastandingsservice.transformer.StandingsModelTransformer;
import com.zlatenov.spoilerfreesportsapi.model.dto.GameInformationDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.TeamScoreDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.TeamsDto;
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
    public void calculateStandings() throws ParseException {
        List<TeamDto> teamDtos = fetchTeamsFromExternalApi();
        List<GameInformationDto> gameInformationDtos = fetchGamesFromExternalApi();


        Map<Date, List<GameInformationDto>> dateListMap = sortGamesByDate(gameInformationDtos);
        List<String> teamNames = teamDtos.stream().map(TeamDto::getFullName).collect(Collectors.toList());
//        Map<String, List<Standings>> teamStandings = new HashMap<>();
//        for (String teamName : teamNames) {
//            teamStandings.put(teamName, new ArrayList<>());
//        }

        List<Standings> standings = new ArrayList<>();

        Map<Date, Map<String, Standings>> standingsMap = new HashMap<>();

        for (Map.Entry<Date, List<GameInformationDto>> dateListEntry : dateListMap.entrySet()) {
            List<GameInformationDto> gamesOnDate = dateListEntry.getValue();
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

    private List<Standings> createStandingsForTeams(List<Standings> standings, GameInformationDto game) {
        TeamScoreDto homeTeam = game.getHomeTeam();
        TeamScoreDto awayTeam = game.getAwayTeam();

        //boolean areFromSameConference = homeTeam.ge

//        List<Standings> homeTeamStandings = standings.stream()
//                .filter(standing -> standing.getTeamName().equalsIgnoreCase(homeTeam.getFullName()))
//                .collect(Collectors.toList());
//
//        List<Standings> awayTeamStandings = standings.stream()
//                .filter(standing -> standing.getTeamName().equalsIgnoreCase(awayTeam.getFullName()))
//                .collect(Collectors.toList());

        String winnersName = determineWinnersName(game);
        if (homeTeam.getFullName().equalsIgnoreCase(winnersName)){
            Optional<Standings> lastStanding = getLastStanding(winnersName, standings);
            if (lastStanding.isPresent()) {

            }
        }
        return null;
    }

    private void calculateTeamStandings(List<GameInformationDto> gamesOnDate, List<Standings> standings)
            throws ParseException {
        Standings lastStanding = standings.get(standings.size() - 1);
        String teamName = lastStanding.getTeamName();
        Optional<GameInformationDto> gameForTeam = gamesOnDate.stream()
                .filter(gameInformationDto -> gameInformationDto.getHomeTeam().getFullName().equalsIgnoreCase(teamName)
                        || gameInformationDto.getAwayTeam().getFullName().equalsIgnoreCase(teamName))
                .findFirst();
        if(gameForTeam.isPresent()) {
            GameInformationDto gameInformationDto = gameForTeam.get();
            String winnersName = determineWinnersName(gameInformationDto);
        }
        else {
            standings.add(Standings.builder().teamName(teamName).date(DATE_FORMAT.parse(gamesOnDate.get(0).getStartTime()))
                                  .build());
        }
    }

    private String determineWinnersName(GameInformationDto gameInformationDto) {
        TeamScoreDto awayTeam = gameInformationDto.getAwayTeam();
        TeamScoreDto homeTeam = gameInformationDto.getHomeTeam();
        if(awayTeam.getPoints() > homeTeam.getPoints()){
            return awayTeam.getFullName();
        }
        return homeTeam.getFullName();
    }

    private Map<Date, List<GameInformationDto>> sortGamesByDate(List<GameInformationDto> gameInformationDtos) throws ParseException {
        Date today = Date.from(Instant.now());
        Map<Date, List<GameInformationDto>> gamesToDate = new TreeMap<>();
        for (GameInformationDto gameInformationDto : gameInformationDtos) {
            Date gameStartDate = DATE_FORMAT.parse(gameInformationDto.getStartTime());
            if (gameStartDate.before(today) && (gameInformationDto.getHomeTeam().getPoints() != null
                            && gameInformationDto.getAwayTeam().getPoints() != null)) {
                if(!gamesToDate.containsKey(gameStartDate)) {
                    gamesToDate.put(gameStartDate, new ArrayList<>());
                }
                gamesToDate.get(gameStartDate).add(gameInformationDto);
            }
        }
        return gamesToDate;
    }

    private List<GameInformationDto> fetchGamesFromExternalApi() {
        GamesDto gamesDto = webClientBuilder.build()
                .get()
                .uri("localhost:8083/games")
                .retrieve()
                .bodyToMono(GamesDto.class)
                .block();

        return gamesDto.getGameInformationDtos();
    }

    private List<TeamDto> fetchTeamsFromExternalApi() {
        TeamsDto teamsDto = webClientBuilder.build()
                .get()
                .uri("localhost:8087/teams")
                .retrieve()
                .bodyToMono(TeamsDto.class)
                .block();

        return teamsDto.getTeamDtos();
    }
}
