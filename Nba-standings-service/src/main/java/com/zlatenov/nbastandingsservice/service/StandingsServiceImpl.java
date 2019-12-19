package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.entity.Standings;
import com.zlatenov.nbastandingsservice.model.response.RapidApiStandingsResponse;
import com.zlatenov.nbastandingsservice.model.response.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.response.TeamResponseModel;
import com.zlatenov.nbastandingsservice.model.service.Game;
import com.zlatenov.nbastandingsservice.model.service.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.service.Team;
import com.zlatenov.nbastandingsservice.model.transformer.GamesTransformer;
import com.zlatenov.nbastandingsservice.model.transformer.StandingsModelTransformer;
import com.zlatenov.nbastandingsservice.model.transformer.TeamsTransformer;
import com.zlatenov.nbastandingsservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.nbastandingsservice.repository.StandingsRepository;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.team.TeamsDto;
import com.zlatenov.spoilerfreesportsapi.model.exception.UnresponsiveAPIException;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Record;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Score;
import com.zlatenov.spoilerfreesportsapi.model.pojo.Streak;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.zlatenov.spoilerfreesportsapi.util.DateUtil.getCurrentDateWithoutTime;
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
    private final TeamsTransformer teamsTransformer;

    private final ModelMapper modelMapper;

    @Override
    public void initializeDatabase() throws IOException, UnresponsiveAPIException {
        List<StandingsServiceModel> standingsServiceModels = initStandingsData();

        List<StandingsServiceModel> teamServiceModelsFromDB = standingsModelTransformer.transformEntitiesToStandingsServiceModels(
                standingsRepository.findAll());

        if (CollectionUtils.isEmpty(teamServiceModelsFromDB)) {
            saveStandings(standingsServiceModels);
        }
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

        List<StandingsServiceModel> standingsServiceModels = standingsModelTransformer.transformResponseToTeamServiceModels(
                standingsResponseModels);
        standingsServiceModels.forEach(standingsServiceModel -> standingsServiceModel.setDate(getCurrentDateWithoutTime()));
        return standingsServiceModels;
    }

    @Override
    public void calculateStandings() throws UnresponsiveAPIException {
        List<TeamDto> teamDtos = fetchTeamsFromExternalApi();
        List<GameDto> gameDtos = fetchGamesFromExternalApi();

        List<Team> teams = teamsTransformer.transformDtoListToTeams(teamDtos);
        List<Game> games = gamesTransformer.transformDtoListToGames(gameDtos, teams);

        Map<Date, List<Game>> dateListMap = sortGamesByDate(games);
        List<StandingsServiceModel> standingsServiceModelList = new ArrayList<>();

        List<StandingsServiceModel> currentTeamStanding = createInitialStandingsForTeams(teams);

        dateListMap.keySet()
                .forEach(date -> standingsServiceModelList.addAll(
                        simulateStandingsForDate(date, currentTeamStanding, dateListMap.get(date))));
        System.out.println();
    }

    @Override
    public List<StandingsServiceModel> getStandingsForDate(Date date) {
        List<StandingsServiceModel> standingsServiceModels = standingsModelTransformer.transformEntitiesToStandingsServiceModels(
                standingsRepository.findByDate(date));
        standingsServiceModels.sort(
                Comparator.comparing(standingsServiceModel -> standingsServiceModel.getStreak().getWinPercentage())
        );
        return standingsServiceModels;
    }

    private List<StandingsServiceModel> simulateStandingsForDate(Date date,
                                                                 List<StandingsServiceModel> currentTeamStanding, List<Game> games) {
        List<StandingsServiceModel> standingsServiceModels = remapStandingsServiceModelsToNextDay(currentTeamStanding,
                date);
        games.forEach(game -> changeStandingsDateAfterGame(game, standingsServiceModels));
        currentTeamStanding = standingsServiceModels;
        return standingsServiceModels;
    }

    private void changeStandingsDateAfterGame(Game game, List<StandingsServiceModel> standingsServiceModels) {
        Team winner = game.getWinner();
        Team looser = game.getLooser();
        boolean isSameConferenceTeamsGame = game.isSameConferenceTeamsGame();
        boolean isSameDivisionsTeamsGame = game.isSameDivisionTeamsGame();
        standingsServiceModels.stream()
                .filter(standingsServiceModel -> standingsServiceModel.getTeam()
                        .getName()
                        .equalsIgnoreCase(winner.getName()))
                .findFirst()
                .ifPresent(standingsServiceModel -> changeRecordOfWinner(standingsServiceModel,
                        isSameConferenceTeamsGame,
                        isSameDivisionsTeamsGame));
        standingsServiceModels.stream()
                .filter(standingsServiceModel -> standingsServiceModel.getTeam()
                        .getName()
                        .equalsIgnoreCase(looser.getName()))
                .findFirst()
                .ifPresent(standingsServiceModel -> changeRecordOLooser(standingsServiceModel,
                        isSameConferenceTeamsGame,
                        isSameDivisionsTeamsGame));
    }

    private void changeRecordOLooser(StandingsServiceModel standingsServiceModel, boolean isSameConferenceTeamsGame,
                                     boolean isSameDivisionsTeamsGame) {
        Record teamRecord = standingsServiceModel.getTeamRecord();
        Record conferenceRecord = standingsServiceModel.getConferenceRecord();
        Record divisionRecord = standingsServiceModel.getDivisionRecord();
        Streak streak = standingsServiceModel.getStreak();
        teamRecord.incrementNumberOfLosses();
        if (isSameConferenceTeamsGame) {
            conferenceRecord.incrementNumberOfLosses();
        }
        if (isSameDivisionsTeamsGame) {
            divisionRecord.incrementNumberOfLosses();
        }

        streak.clearWinningStreak();
        streak.recalculatePct(teamRecord);
    }


    private void changeRecordOfWinner(StandingsServiceModel standingsServiceModel, boolean isSameConferenceTeamsGame,
                                      boolean isSameDivisionsTeamsGame) {
        Record teamRecord = standingsServiceModel.getTeamRecord();
        Record conferenceRecord = standingsServiceModel.getConferenceRecord();
        Record divisionRecord = standingsServiceModel.getDivisionRecord();
        Streak streak = standingsServiceModel.getStreak();
        teamRecord.incrementNumberOfWins();
        if (isSameConferenceTeamsGame) {
            conferenceRecord.incrementNumberOfWins();
        }
        if (isSameDivisionsTeamsGame) {
            divisionRecord.incrementNumberOfWins();
        }

        streak.incrementWinningStreak();
        streak.recalculatePct(teamRecord);
    }

    private List<StandingsServiceModel> remapStandingsServiceModelsToNextDay(
            List<StandingsServiceModel> currentTeamStanding, Date date) {
        return currentTeamStanding.stream()
                .map(standingsServiceModel -> remapStandingsServiceModelToNextDay(standingsServiceModel, date))
                .collect(Collectors.toList());
    }

    private StandingsServiceModel remapStandingsServiceModelToNextDay(StandingsServiceModel currentTeamStanding, Date date) {
        StandingsServiceModel standingsServiceModel = new StandingsServiceModel();
        modelMapper.map(currentTeamStanding, standingsServiceModel);
        standingsServiceModel.setDate(date);
        return standingsServiceModel;
    }

    private List<StandingsServiceModel> createInitialStandingsForTeams(List<Team> teams) {
        return teams.stream()
                .map(team -> StandingsServiceModel.builder()
                        .team(team)
                        .teamRecord(Record.createEmptyRecord())
                        .conferenceRecord(Record.createEmptyRecord())
                        .divisionRecord(Record.createEmptyRecord())
                        .streak(Streak.createEmptyStreak())
                        .build())
                .collect(Collectors.toList());
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


    private Map<Date, List<Game>> sortGamesByDate(List<Game> games) {
        Map<Date, List<Game>> gamesToDate = new TreeMap<>();
        for (Game game : games) {
            Score score = game.getScore();
            if (game.getDate().before(getCurrentDateWithoutTime()) && (score != null
                    && score.getHomeTeamPoints() != null && score.getAwayTeamPoints() != null)) {
                if (!gamesToDate.containsKey(game.getDate())) {
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

    @Override
    public List<StandingsServiceModel> getCurrentStandings() {
        List<StandingsServiceModel> standingsServiceModels = standingsModelTransformer.transformEntitiesToStandingsServiceModels(
                standingsRepository.findAll());
        standingsServiceModels.sort(
                Comparator.comparing(standingsServiceModel -> standingsServiceModel.getStreak().getWinPercentage())
        );
        return standingsServiceModels;
    }
}
