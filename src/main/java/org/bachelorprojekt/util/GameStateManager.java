package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.inventory.InventoryScreen;
import org.bachelorprojekt.util.json.JsonLoader;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Map;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.HashMap;
import java.util.List;

public class GameStateManager {
    private final Engine engine;
    private final Player player;

    private final HashMap<Integer, Chapter> chapters;
    private final HashMap<Integer, Location> locations;
    private final HashMap<Integer, Quest> quests;
    private final HashMap<Integer, Map> maps;

    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;

    /* Current Chapter information */
    private int currentChapterIndex;

    // GameStateManager is created with an Engine and a Player
    public GameStateManager(Engine engine, Player player, List<Chapter> chapterList, List<Location> locationList, List<Quest> questList, List<Map> mapList) {
        this.engine = engine;
        this.player = player;

        this.chapters = new HashMap<>();
        this.locations = new HashMap<>();
        this.quests = new HashMap<>();
        this.maps = new HashMap<>();

        for (Chapter chapter : chapterList) {
            chapters.put(chapter.getId(), chapter);
        }
        for (Location location : locationList) {
            locations.put(location.getId(), location);
        }
        for (Quest quest : questList) {
            quests.put(quest.getId(), quest);
        }
        for (Map map : mapList) {
            maps.put(map.getId(), map);
        }


        // Set the current chapter to the first chapter
        currentChapterIndex = 1;
    }

    // Direktzugriff für Kapitel
    public Chapter getChapterById(int chapterId) {
        return chapters.get(chapterId);
    }

    // Direktzugriff für Locations
    public Location getLocationById(int locationId) {
        return locations.get(locationId);
    }

    // Direktzugriff für Quests
    public Quest getQuestById(int questId) {
        return quests.get(questId);
    }

    // Direktzugriff für Maps
    public Map getMapById(int mapId) {
        return maps.get(mapId);
    }

    // Für mehrere IDs: Erzeuge eine Liste der Objekte
    public List<Location> getLocationsForChapter(List<Integer> locationIds) {
        return locationIds.stream().map(locations::get).toList();
    }

    public List<Quest> getQuestsForChapter(List<Integer> questIds) {
        return questIds.stream().map(quests::get).toList();
    }








    public Player getPlayer() {
        return player;
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public void setInventoryOpen(boolean isOpen) {
        isInventoryOpen = isOpen;
        if (isOpen) {
            engine.pushScreen(new InventoryScreen(engine, player)); // Wechsle zum Inventar-Screen
        } else {
            engine.popScreen(); // Kehre zum vorherigen Screen zurück (z. B. Kapitel)
        }
    }

    public boolean isMapOpen() {
        return isMapOpen;
    }

    public void setMapOpen(boolean isOpen) {
        isMapOpen = isOpen;
        if (isOpen) {
            engine.pushScreen(new MapScreen(engine, maps.get(currentChapterIndex).getLayout(), 550, 550));
        } else {
            engine.popScreen(); // Kehre zum vorherigen Screen zurück (z. B. Kapitel)
        }
    }

}
