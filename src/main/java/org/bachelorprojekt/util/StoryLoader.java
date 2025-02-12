package org.bachelorprojekt.util;

import org.bachelorprojekt.manager.*;
import org.bachelorprojekt.util.json.JsonLoader;
import org.bachelorprojekt.util.json.jackson.*;

import java.util.List;

/**
 * Loads and manages the story data for the game by parsing JSON files and initializing various managers.
 * This class is responsible for loading all necessary game data such as chapters, locations, quests, items, NPCs, and enemies,
 * and providing access to the corresponding manager instances.
 */
public class StoryLoader {

    private final String chaptersFile = "json/chapters.json";
    private final String locationsFile = "json/locations.json";
    private final String questsFile = "json/quests.json";
    private final String mapsFile = "json/maps.json";
    private final String itemsFile = "json/items.json";
    private final String npcsFile = "json/npcs.json";
    private final String enemiesFile = "json/enemies.json";

    private final ChapterManager chapterManager;
    private final LocationManager locationManager;
    private final QuestManager questManager;
    private final MapManager mapManager;
    private final ItemManager itemManager;
    private final NpcManager npcManager;
    private final EnemyManager enemyManager;

    /**
     * Constructs a new StoryLoader and initializes the necessary managers by loading data from JSON files.
     * 
     * @throws RuntimeException if there is an error during the loading of the story data
     */
    public StoryLoader() {
        try {
            // JSON-Dateien laden
            List<Chapter> chapterList = JsonLoader.loadChapters(chaptersFile);
            List<Location> locationList = JsonLoader.loadLocations(locationsFile);
            List<Quest> questList = JsonLoader.loadQuests(questsFile);
            List<Maps> mapList = JsonLoader.loadMaps(mapsFile);
            List<Item> itemList = JsonLoader.loadItems(itemsFile);
            List<NPC> npcList = JsonLoader.loadNpcs(npcsFile);
            List<Enemy> enemyList = JsonLoader.loadEnemies(enemiesFile);

            // Manager initialisieren
            this.chapterManager = new ChapterManager(chapterList, questList, locationList, mapList);
            this.locationManager = new LocationManager(locationList, npcList, itemList);
            this.npcManager = new NpcManager(npcList, locationList);
            this.itemManager = new ItemManager(itemList);
            this.mapManager = new MapManager(mapList, locationList);
            this.questManager = new QuestManager(questList, npcList, itemList, locationList);
            this.enemyManager = new EnemyManager(enemyList, locationList, itemList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Laden der Story-Daten.", e);
        }
    }

    // =================================================================================================================
    // ==                                      Getter-Methoden für Manager                                            ==
    // =================================================================================================================

    /**
     * Returns the manager responsible for handling chapter data.
     *
     * @return the ChapterManager instance
     */
    public ChapterManager getChapterManager() {
        return chapterManager;
    }

    /**
     * Returns the manager responsible for handling location data.
     *
     * @return the LocationManager instance
     */
    public LocationManager getLocationManager() {
        return locationManager;
    }

    /**
     * Returns the manager responsible for handling quest data.
     *
     * @return the QuestManager instance
     */
    public QuestManager getQuestManager() {
        return questManager;
    }

    /**
     * Returns the manager responsible for handling map data.
     *
     * @return the MapManager instance
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Returns the manager responsible for handling item data.
     *
     * @return the ItemManager instance
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * Returns the manager responsible for handling NPC data.
     *
     * @return the NpcManager instance
     */
    public NpcManager getNpcManager() {
        return npcManager;
    }

    /**
     * Returns the manager responsible for handling enemy data.
     *
     * @return the EnemyManager instance
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
}
