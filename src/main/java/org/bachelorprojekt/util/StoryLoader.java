package org.bachelorprojekt.util;

import org.bachelorprojekt.manager.*;
import org.bachelorprojekt.util.json.JsonLoader;
import org.bachelorprojekt.util.json.jackson.*;

import java.util.List;

public class StoryLoader {

    private final String chaptersFile = "json/chapters.json";
    private final String locationsFile = "json/locations.json";
    private final String questsFile = "json/quests.json";
    private final String mapsFile = "json/maps.json";
    private final String itemsFile = "json/items.json";
    private final String npcsFile = "json/npcs.json";

    private final ChapterManager chapterManager;
    private final LocationManager locationManager;
    private final QuestManager questManager;
    private final MapManager mapManager;
    private final ItemManager itemManager;
    private final NpcManager npcManager;

    public StoryLoader() {

        try {
            // JSON-Dateien laden
            List<Chapter> chapterList = JsonLoader.loadChapters(chaptersFile);
            List<Location> locationList = JsonLoader.loadLocations(locationsFile);
            List<Quest> questList = JsonLoader.loadQuests(questsFile);
            List<Maps> mapList = JsonLoader.loadMaps(mapsFile);
            List<Item> itemList = JsonLoader.loadItems(itemsFile);
            List<NPC> npcList = JsonLoader.loadNpcs(npcsFile);

            // Manager initialisieren
            this.chapterManager = new ChapterManager(chapterList, questList, locationList, mapList);
            this.locationManager = new LocationManager(locationList, npcList);
            this.npcManager = new NpcManager(npcList, locationList);
            this.itemManager = new ItemManager(itemList);
            this.mapManager = new MapManager(mapList, locationList);
            this.questManager = new QuestManager(questList, npcList, itemList, locationList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Laden der Story-Daten.", e);
        }
    }

    // =================================================================================================================
    // ==                                      Getter-Methoden für Manager                                            ==
    // =================================================================================================================

    public ChapterManager getChapterManager() {
        return chapterManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public NpcManager getNpcManager() {
        return npcManager;
    }
}
