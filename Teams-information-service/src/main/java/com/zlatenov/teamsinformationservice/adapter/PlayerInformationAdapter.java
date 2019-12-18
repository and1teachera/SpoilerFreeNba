package com.zlatenov.teamsinformationservice.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zlatenov.teamsinformationservice.model.response.PlayerInfo;
import com.zlatenov.teamsinformationservice.model.response.PlayerInformation;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Angel Zlatenov
 */

public class PlayerInformationAdapter extends TypeAdapter<PlayerInformation> {


    @Override
    public void write(JsonWriter out, PlayerInformation value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.getLeagues().toString());
    }

    @Override
    public PlayerInformation read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldName = null;
        PlayerInformation playerInformation = new PlayerInformation(new ArrayList<>());
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
                    PlayerInfo playerInfo = new PlayerInfo();
                    if (token.equals(JsonToken.NAME)) {
                        fieldName = reader.nextName();
                        if ("jersey".equals(fieldName)) {
                            playerInfo.setJersey(reader.nextString());
                            fieldName = reader.nextName();
                        }
                        if ("active".equals(fieldName)) {
                            playerInfo.setActive(reader.nextString());
                            fieldName = reader.nextName();
                        }
                        if ("pos".equals(fieldName)) {
                            playerInfo.setPos(reader.nextString());
                        }
                    }
                    playerInformation.getLeagues().add(playerInfo);
                    reader.endObject();
                }
            }
        }
        reader.endObject();
        return playerInformation;
    }
}
