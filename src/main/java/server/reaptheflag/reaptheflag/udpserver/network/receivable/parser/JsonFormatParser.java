package server.reaptheflag.reaptheflag.udpserver.network.receivable.parser;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.reaptheflag.reaptheflag.udpserver.network.receivable.parser.DataFormatParser;

import java.util.ArrayList;
import java.util.List;

/**
 *  This module provides functions of GSON operation
 *  TODO: add recursive parsse
 */
public class JsonFormatParser<E> extends DataFormatParser <String>{

    private static GsonBuilder builder = new GsonBuilder();
    private static JsonParser parser = new JsonParser();
    private static Logger LOGGER = LogManager.getLogger();
    private Gson gson;
    private Class<E> target;
    private JsonElement structure;

    public <T> JsonFormatParser(String data, Class<E> target) throws JsonSyntaxException{
        super(data);
        this.gson = builder.create();
        this.target = target;
        structure = parser.parse(data.trim());
    }

    @Override
    public String getAttributeByName(String name) {
        if (!structure.isJsonObject()) {
            return null;
        }
        JsonElement ele = getGsonElementByName(name, structure);
        return ele == null ? "invalid" : ele.getAsString();
    }

    @Override
    public int getIntByName(String name) {
        if (!structure.isJsonObject()) {
            return 0;
        }
        JsonElement ele = getGsonElementByName(name, structure);
        return ele == null ? 0 : ele.getAsInt();
    }

    @Override
    public List<String> getAttributeArray(String name) {
        if (!structure.isJsonObject()) {
            return null;
        }
        List<String> res = new ArrayList<>();
        JsonArray array = getGsonListByName(name, structure);

        if (array == null) {
            return res;
        }

        for (JsonElement e : array) {
            res.add(e.getAsString());
        }

        return res;
    }

    public <T> T createInstanceUnsafe(Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }
    public E createSafeInstance() {
        return createInstanceUnsafe(target);
    }

    private JsonElement getGsonElementByName(String name, JsonElement node) {
        if (!node.isJsonObject()) {
            return null;
        }
        return node.getAsJsonObject().get(name);
    }

    private JsonArray getGsonListByName(String name, JsonElement node) {
        if (!node.isJsonObject()) {
            return null;
        }
        return  node.getAsJsonObject().get(name).getAsJsonArray();
    }
}
