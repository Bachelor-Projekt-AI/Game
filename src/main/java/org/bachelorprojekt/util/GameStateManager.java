package org.bachelorprojekt.util;

import com.badlogic.gdx.Screen;
import org.bachelorprojekt.game.Chapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.inventory.InventoryScreen;

import java.util.List;
import java.util.Stack;

public class GameStateManager {
    private final Engine engine;
    private Player player;
    private boolean isInventoryOpen = false;
    private boolean isMapOpen = false;
    private final Stack<Screen> screenStack;

    public GameStateManager(Engine engine, Player player) {
        this.engine = engine;
        this.player = player;
        this.screenStack = engine.getScreenStack();
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
}
