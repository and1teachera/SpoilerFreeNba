package com.zlatenov.teamsinformationservice.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.zlatenov.teamsinformationservice.adapter.LeaguesAdapter;
import com.zlatenov.teamsinformationservice.model.Leagues;
import com.zlatenov.teamsinformationservice.model.RapidApiTeamsResponse;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */
@Component
public class ExternalAPIContentProcessor {

    public RapidApiTeamsResponse processTeamsAPIResponse(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new GsonBuilder().registerTypeAdapter(Leagues.class, new LeaguesAdapter()).create();
        return gson.fromJson(
                JsonParser.parseString(responseBodyString)
                        .getAsJsonObject().get("api").toString(),
                RapidApiTeamsResponse.class);
    }
}
