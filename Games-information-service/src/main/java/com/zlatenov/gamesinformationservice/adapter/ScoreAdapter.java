package com.zlatenov.gamesinformationservice.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zlatenov.gamesinformationservice.model.Score;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author Angel Zlatenov
 */

public class ScoreAdapter extends TypeAdapter<Score> {

    @Override
    public void write(JsonWriter out, Score value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.getPoints());
    }

    @Override
    public Score read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldName = null;
        Score score = null;
        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldName = reader.nextName();
            }
            if ("points".equals(fieldName)) {
                //move to next token
                String points = reader.nextString();
                try {
                    if (!StringUtils.isEmpty(points)) {
                        score = new Score(Short.valueOf(points));
                    }
                }
                catch (NumberFormatException e) {
                    reader.endObject();
                    return score;
                }
            }
        }
        reader.endObject();
        return score;
    }
}
