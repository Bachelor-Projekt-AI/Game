package org.bachelorprojekt.util;

import org.bachelorprojekt.chapter.ChapterSystem;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.inventory.InventoryScreen;
import org.bachelorprojekt.manager.*;
import org.bachelorprojekt.quest.QuestSystem;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;

public class GameSystemManager {
    private final Engine engine;
    private final Player player;
    private final StoryLoader storyLoader;
    private final QuestSystem questSystem;
    private final ChapterSystem chapterSystem;

    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;

    /**
     * Erstellt den GameSystemManager mit Engine und Player
     */
    public GameSystemManager(Engine engine, Player player, int chapterIndex) {
        this.engine = engine;
        this.player = player;
        this.storyLoader = new StoryLoader(); // Lädt alle Story-Daten
        this.questSystem = new QuestSystem(storyLoader.getQuestManager().getAllQuests(), player, engine);
        this.chapterSystem = new ChapterSystem(engine, chapterIndex, this);
        questSystem.startQuest(1);
        questSystem.startQuest(2);
        questSystem.startQuest(3);

        player.addToInventory(storyLoader.getItemManager().getItemById(4));
        player.addToInventory(storyLoader.getItemManager().getItemById(5));
        player.addToInventory(storyLoader.getItemManager().getItemById(6));
        player.addToInventory(storyLoader.getItemManager().getItemById(7));
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

    public void closeMap() {
        isMapOpen = false;
        engine.popScreen();
    }

    public void openMapWithChapter(ChapterScreen chapter) {
        isMapOpen = true;
        // TODO auslagern in chatperSystem!
        engine.pushScreen(new MapScreen(engine, chapterSystem.getCurrentChapter().getMap(), 650, chapter));
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

    public EnemyManager getEnemyManager() {
        return storyLoader.getEnemyManager();
    }

    public ChapterManager getChapterManager() {
        return storyLoader.getChapterManager();
    }

    /**
     * Gibt die aktuelle Kapitel-Instanz zurück.
     */
    // TODO auslagern in chatperSystem!
    public Chapter getCurrentChapter() {
        return chapterSystem.getCurrentChapter();
    }

    public ChapterSystem getChapterSystem() {
        return chapterSystem;
    }
}
