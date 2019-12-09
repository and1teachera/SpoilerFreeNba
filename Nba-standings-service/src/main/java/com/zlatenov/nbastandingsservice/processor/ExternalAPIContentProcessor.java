package com.zlatenov.nbastandingsservice.processor;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.zlatenov.nbastandingsservice.model.RapidApiStandingsResponse;
import com.zlatenov.nbastandingsservice.model.TeamResponseModel;
import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

@Component
@AllArgsConstructor
public class ExternalAPIContentProcessor {

    private Gson gson;

    public RapidApiStandingsResponse processStandingsAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiStandingsResponse.class);
    }

    public List<TeamResponseModel> processTeamsAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString).getAsJsonObject().get("api").toString(),
                TeamsResponse.class).getTeams();

    }
}
