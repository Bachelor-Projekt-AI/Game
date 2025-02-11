package org.bachelorprojekt.util.json;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bachelorprojekt.util.json.jackson.*;
import java.util.List;

public class JsonLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Chapter> loadChapters(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Chapter.class));
    }

    public static List<Item> loadItems(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Item.class));
    }

    public static List<Location> loadLocations(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Location.class));
    }

    public static List<Maps> loadMaps(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Maps.class));
    }

    public static List<Quest> loadQuests(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Quest.class));
    }

    public static List<NPC> loadNpcs(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, NPC.class));
    }

    public static List<Enemy> loadEnemies(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Enemy.class));
    }

}
