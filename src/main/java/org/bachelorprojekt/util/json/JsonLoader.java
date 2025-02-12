package org.bachelorprojekt.util.json;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bachelorprojekt.util.json.jackson.*;
import java.util.List;

/**
 * A utility class to load JSON files and map them to the appropriate Java objects.
 * The class uses Jackson's ObjectMapper to parse JSON data into lists of objects.
 */
public class JsonLoader {

    private static final ObjectMapper mapper = new ObjectMapper(); // Jackson ObjectMapper for JSON parsing

    /**
     * Loads a list of Chapter objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Chapter objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Chapter> loadChapters(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Chapter.class));
    }

    /**
     * Loads a list of Item objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Item objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Item> loadItems(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Item.class));
    }

    /**
     * Loads a list of Location objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Location objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Location> loadLocations(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Location.class));
    }

    /**
     * Loads a list of Maps objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Maps objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Maps> loadMaps(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Maps.class));
    }

    /**
     * Loads a list of Quest objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Quest objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Quest> loadQuests(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Quest.class));
    }

    /**
     * Loads a list of NPC objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of NPC objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<NPC> loadNpcs(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, NPC.class));
    }

    /**
     * Loads a list of Enemy objects from a JSON file.
     * 
     * @param filePath The path to the JSON file (relative to the assets folder).
     * @return A list of Enemy objects.
     * @throws Exception if an error occurs during the reading or parsing of the file.
     */
    public static List<Enemy> loadEnemies(String filePath) throws Exception {
        return mapper.readValue(Gdx.files.internal(filePath).read(),
                mapper.getTypeFactory().constructCollectionType(List.class, Enemy.class));
    }
}
