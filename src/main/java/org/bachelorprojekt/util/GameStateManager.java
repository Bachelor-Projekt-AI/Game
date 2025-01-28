package org.bachelorprojekt.util;

import org.bachelorprojekt.game.Chapter;
import org.bachelorprojekt.character.Player;

import java.util.List;

public class GameStateManager {
    private List<Chapter> chapters; // Story aus JSON geladen
    private Player player;          // Spielerreferenz
    private boolean isInventoryOpen = false;

    public GameStateManager(List<Chapter> chapters, Player player) {
        this.chapters = chapters;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Chapter getCurrentChapter() {
        return chapters.get(player.getCurrentChapterIndex());
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public void setInventoryOpen(boolean inventoryOpen) {
        isInventoryOpen = inventoryOpen;
    }
}
