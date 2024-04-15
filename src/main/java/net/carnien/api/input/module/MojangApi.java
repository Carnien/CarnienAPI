package net.carnien.api.input.module;

import net.carnien.api.Carnien;
import net.carnien.api.input.Module;
import org.json.simple.JSONObject;

import java.util.UUID;
import java.util.regex.Pattern;

public class MojangApi extends Module {

    public MojangApi(Carnien carnien) {
        super(carnien);
    }

    public UUID getUuid(String playerName) {
        final String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        final JsonHelper jsonHelper = (JsonHelper) getCarnien().getModule("JsonHelper");
        final JSONObject json = jsonHelper.getJson(url);

        if (json == null) return null;

        String uuidStr = (String) json.get("id");
        final Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
        uuidStr = pattern.matcher(uuidStr).replaceAll("$1-$2-$3-$4-$5");
        return UUID.fromString(uuidStr);
    }

    public String getName(String playerName) {
        final String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        final JsonHelper jsonHelper = (JsonHelper) getCarnien().getModule("JsonHelper");
        final JSONObject json = jsonHelper.getJson(url);

        if (json == null) return null;

        return (String) json.get("name");
    }

}