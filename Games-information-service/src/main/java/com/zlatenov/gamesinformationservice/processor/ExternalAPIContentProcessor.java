package com.zlatenov.gamesinformationservice.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.zlatenov.gamesinformationservice.adapter.LeaguesAdapter;
import com.zlatenov.gamesinformationservice.adapter.ScoreAdapter;
import com.zlatenov.gamesinformationservice.model.Leagues;
import com.zlatenov.gamesinformationservice.model.RapidApiGamesResponse;
import com.zlatenov.gamesinformationservice.model.RapidApiTeamsResponse;
import com.zlatenov.gamesinformationservice.model.Score;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@Component
public class ExternalAPIContentProcessor {

    public RapidApiGamesResponse processGamesAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new GsonBuilder().registerTypeAdapter(Score.class, new ScoreAdapter()).create();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiGamesResponse.class);
    }

    public RapidApiTeamsResponse processTeamsAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new GsonBuilder().registerTypeAdapter(Leagues.class, new LeaguesAdapter()).create();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiTeamsResponse.class);
    }
}
