package com.zlatenov.teamsinformationservice.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.zlatenov.teamsinformationservice.adapter.LeaguesAdapter;
import com.zlatenov.teamsinformationservice.adapter.PlayerInformationAdapter;
import com.zlatenov.teamsinformationservice.model.response.Leagues;
import com.zlatenov.teamsinformationservice.model.response.PlayerInformation;
import com.zlatenov.teamsinformationservice.model.response.RapidApiPlayersResponseModel;
import com.zlatenov.teamsinformationservice.model.response.RapidApiTeamsResponseModel;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@Component
public class ExternalAPIContentProcessor {

    public RapidApiTeamsResponseModel processTeamsAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new GsonBuilder().registerTypeAdapter(Leagues.class, new LeaguesAdapter()).create();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiTeamsResponseModel.class);
    }

    public RapidApiPlayersResponseModel processPlayersAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new GsonBuilder().registerTypeAdapter(PlayerInformation.class, new PlayerInformationAdapter()).create();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiPlayersResponseModel.class);
    }
}
