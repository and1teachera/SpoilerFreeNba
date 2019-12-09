package com.zlatenov.nbastandingsservice.service;

import com.zlatenov.nbastandingsservice.model.RapidApiStandingsResponse;
import com.zlatenov.nbastandingsservice.model.StandingsResponseModel;
import com.zlatenov.nbastandingsservice.model.StandingsServiceModel;
import com.zlatenov.nbastandingsservice.model.TeamResponseModel;
import com.zlatenov.nbastandingsservice.processor.ExternalAPIContentProcessor;
import com.zlatenov.nbastandingsservice.transformer.StandingsModelTransformer;
import com.zlatenov.nospoilersportsapi.model.exception.UnresponsiveAPIException;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class StandingsServiceImpl implements StandingsService {

    private final WebClient.Builder webClientBuilder;
    private final OkHttpClient client;
    private final ExternalAPIContentProcessor processor;
    private final StandingsModelTransformer standingsModelTransformer;

    @Override
    public void initializeDatabase() throws IOException, UnresponsiveAPIException {
        List<StandingsServiceModel> standingsServiceModels = initStandingsData();
        System.out.println();
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
}
