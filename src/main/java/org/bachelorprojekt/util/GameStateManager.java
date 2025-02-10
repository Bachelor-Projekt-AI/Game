package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.json.jackson.*;

import org.bachelorprojekt.inventory.InventoryScreen;
import org.bachelorprojekt.util.json.JsonLoader;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;

public class GameStateManager {
    private final Engine engine;
    private final Player player;

    private final HashMap<Integer, Chapter> chapters;
    private final HashMap<Integer, Location> locations;
    private final HashMap<Integer, Quest> quests;
    private final HashMap<Integer, Map> maps;
    private final HashMap<Integer, Item> items;
    private final HashMap<String, NPC> npcs;

    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;

    /* Current Chapter information */
    private int currentChapterIndex;

    // GameStateManager is created with an Engine and a Player
    public GameStateManager(Engine engine, Player player, List<Chapter> chapterList, List<Location> locationList, List<Quest> questList, List<Map> mapList, List<Item> itemList, List<NPC> npcList) {
        this.engine = engine;
        this.player = player;

        this.chapters = new HashMap<>();
        this.locations = new HashMap<>();
        this.quests = new HashMap<>();
        this.maps = new HashMap<>();
        this.items = new HashMap<>();
        this.npcs = new HashMap<>();

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
        for (Item item : itemList) {
            items.put(item.getId(), item);
        }
        for (NPC npc : npcList) {
            npcs.put(npc.getName(), npc);
        }

        player.setLocation(locations.get(1));
        player.addToInventory(items.get(1)); // Add a test item to the player's inventory

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
        System.out.println(Arrays.toString(locationIds.toArray()));
        return locationIds.stream().map(locations::get).toList();
    }

    public List<Quest> getQuestsForChapter(List<Integer> questIds) {
        return questIds.stream().map(quests::get).toList();
    }


    public void talkToNPC(String npcName) {
        NPC npc = npcs.get(npcName);
        if (npc != null) {
            System.out.println(npc.getDialogues());
            checkQuestProgress(npc);
        } else {
            System.out.println("Kein solcher NPC gefunden.");
        }
    }

    public void checkQuestProgress(NPC npc) {
        List<Quest> activeQuests = player.getActiveQuests();
        for (Quest quest : activeQuests) {
            if (quest.getNpcId() == npc.getId()) {
                System.out.println("Quest fortschrittlich: " + quest.getTitle());
                player.removeActiveQuest(quest);
                player.addCompletedQuest(quest);
            }
        }
    }

    public List<NPC> getNPCsInCurrentLocation() {
        Location currentLocation = player.getLocation();
        List<NPC> npcs = getNpcsForLocation(currentLocation.getId());
        return npcs;
    }

    public List<NPC> getNpcsForLocation(int locationId) {
        return npcs.values().stream().filter(npc -> npc.getLocationId() == locationId).toList();
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
            engine.pushScreen(new InventoryScreen(engine)); // Wechsle zum Inventar-Screen
        } else {
            engine.popScreen(); // Kehre zum vorherigen Screen zurück (z. B. Kapitel)
        }
    }

    public boolean isMapOpen() {
        return isMapOpen;
    }

	public void closeMap() {
		isMapOpen = false;
		engine.popScreen();
	}

    public void openMapWithChapter(ChapterScreen chapter) {
        isMapOpen = true;
        engine.pushScreen(new MapScreen(engine, maps.get(currentChapterIndex), 650, chapter));
    }

    public void setPlayerLocation(Location location) {
        player.setLocation(location);
    }

}
