package com.zlatenov.teamsinformationservice.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zlatenov.teamsinformationservice.model.League;
import com.zlatenov.teamsinformationservice.model.Leagues;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Angel Zlatenov
 */
public class LeaguesAdapter extends TypeAdapter<Leagues> {

    @Override
    public void write(JsonWriter out, Leagues value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.getLeagues().toString());
    }

    @Override
    public Leagues read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldName = null;
        Leagues leagues = new Leagues(new ArrayList<>());
        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldName = reader.nextName();
            }
            if ("standard".equals(fieldName)) {
                //move to next token
                token = reader.peek();

                if (token.equals(JsonToken.BEGIN_OBJECT)) {
                    reader.beginObject();
                    token = reader.peek();
                    League league = new League();
                    if (token.equals(JsonToken.NAME)) {
                        fieldName = reader.nextName();
                        if ("confName".equals(fieldName)) {
                            league.setConfName(reader.nextString());
                            fieldName = reader.nextName();
                        }
                        if ("divName".equals(fieldName)) {
                            league.setDivName(reader.nextString());
                        }
                    }
                    leagues.getLeagues().add(league);
                    reader.endObject();
                }
            }
        }
        reader.endObject();
        return leagues;
    }
}
