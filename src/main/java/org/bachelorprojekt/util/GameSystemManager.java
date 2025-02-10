package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.game.events.EventDispatcher;
import org.bachelorprojekt.inventory.InventoryScreen;
import org.bachelorprojekt.manager.LocationManager;
import org.bachelorprojekt.manager.NpcManager;
import org.bachelorprojekt.manager.QuestManager;
import org.bachelorprojekt.quest.QuestSystem;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Item;

public class GameSystemManager {
    private final Engine engine;
    private final Player player;
    private final StoryLoader storyLoader;
    private final QuestSystem questSystem;

    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;
    private Chapter currentChapter;

    /**
     * Erstellt den GameStateManager mit Engine und Player
     */
    public GameSystemManager(Engine engine, Player player, int chapterIndex) {
        this.engine = engine;
        this.player = player;
        this.storyLoader = new StoryLoader(); // Lädt alle Story-Daten
        this.questSystem = new QuestSystem(storyLoader.getQuestManager().getAllQuests());
        questSystem.startQuest(1);
        questSystem.startQuest(2);
        questSystem.startQuest(3);
        // Setzt das erste Kapitel
        this.currentChapter = storyLoader.getChapterManager().getChapterById(chapterIndex);

    }

    /**
     * Gibt den Spieler zurück.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Öffnet oder schließt das Inventar.
     */
    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public void setInventoryOpen(boolean isOpen) {
        isInventoryOpen = isOpen;
        if (isOpen) {
            engine.pushScreen(new InventoryScreen(engine)); // Öffne Inventar
        } else {
            engine.popScreen(); // Kehre zurück
        }
    }

    /**
     * Öffnet oder schließt die Karte.
     */
    public boolean isMapOpen() {
        return isMapOpen;
    }

    public void setMapOpen(boolean isOpen) {
        isMapOpen = isOpen;
        if (isOpen) {
            engine.pushScreen(new MapScreen(engine, storyLoader.getMapManager().getMapById(currentChapter.getMapId()), 550, 550));
        } else {
            engine.popScreen();
        }
    }

    /**
     * Setzt die Position des Spielers.
     */
    public void setPlayerLocation(Location location) {
        player.setLocation(location);
    }

    /**
     * Gibt den `StoryLoader` zurück.
     * Private Methode, da nur innerhalb des GameStateManagers benötigt.
     */
    private StoryLoader getStoryLoader() {
        return storyLoader;
    }

    /**
     * Gibt das `QuestSystem` zurück.
     */
    public QuestSystem getQuestSystem() {
        return questSystem;
    }

    /**
     * Gibt den `QuestManager` zurück.
     */
    public QuestManager getQuestManager() {
        return storyLoader.getQuestManager();
    }

    /**
     * Gibt den `LocationManager` zurück.
     */
    public LocationManager getLocationManager() {
        return storyLoader.getLocationManager();
    }

    public NpcManager getNpcManager() {
        return storyLoader.getNpcManager();
    }

    /**
     * Gibt die aktuelle Kapitel-Instanz zurück.
     */
    public Chapter getCurrentChapter() {
        return currentChapter;
    }
}
