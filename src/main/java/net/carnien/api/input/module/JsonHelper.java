package net.carnien.api.input.module;

import net.carnien.api.Carnien;
import net.carnien.api.input.Module;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonHelper extends Module {

    public JsonHelper(Carnien carnien) {
        super(carnien);
    }

    public JSONObject getJson(String requestUrl) {
        final InputStream inputStream;

        try {
            final URL url = new URL(requestUrl);
            inputStream = url.openStream();
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final StringBuilder jsonStrBuilder = new StringBuilder();

            int character;
            while ((character = bufferedReader.read()) != -1) jsonStrBuilder.append((char) character);

            final String jsonStr = jsonStrBuilder.toString();
            final JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(jsonStr);
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}