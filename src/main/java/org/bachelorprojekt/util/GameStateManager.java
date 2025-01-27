package org.bachelorprojekt.util;

import com.badlogic.gdx.Screen;
import org.bachelorprojekt.game.Chapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.Story;
import org.bachelorprojekt.inventory.InventoryScreen;

import java.util.List;
import java.util.Stack;

public class GameStateManager {
    private final Engine engine;
    private Player player;
    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;
    private final Stack<Screen> screenStack;
    private Story story;

    // GameStateManager is created with an Engine and a Player
    public GameStateManager(Engine engine, Player player) {
        this.engine = engine;
        this.player = player;
        this.screenStack = engine.getScreenStack();
        story = JsonLoader.loadStory("story/chapters.json");
    }

    // If a GameStateManager is created with an Engine and another GameStateManager, the player and screenStack are copied from the other GameStateManager
    public GameStateManager(Engine engine, GameStateManager gameStateManager) {
        this.engine = engine;
        this.player = gameStateManager.getPlayer();
        this.screenStack = engine.getScreenStack();
    }

    public Story.Chapter getCurrentChapter() {
        // Gib das aktuelle Kapitel basierend auf dem Spielerfortschritt zurück
        int chapterIndex = player.getCurrentChapterIndex();
        return story.getChapters().get(chapterIndex);
    }


    public Screen getCurrentScreen() {
        return screenStack.isEmpty() ? null : screenStack.peek();
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
            engine.getScreenStack().push(new InventoryScreen(engine, player)); // Wechsle zum Inventar-Screen
        } else {
            engine.popScreen(); // Kehre zum vorherigen Screen zurück (z. B. Kapitel)
        }
    }

    public boolean isMapOpen() {
        return isMapOpen;
    }

    public Story getStory() {
        return story;
    }

    public void setCurrentChapter(int chapterIndex) {
        player.setCurrentChapterIndex(chapterIndex);
    }
}
