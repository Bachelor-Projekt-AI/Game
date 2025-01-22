package org.bachelorprojekt.util;

public class GameStateManager {
    private boolean isMainMenu = true;
    private boolean isPaused = false;
    private boolean isMapOpen = false;
    private boolean isAnimatingText = true;

    // Getter und Setter für die Zustände
    public boolean isMainMenu() { return isMainMenu; }
    public void setMainMenu(boolean mainMenu) { isMainMenu = mainMenu; }

    public boolean isPaused() { return isPaused; }
    public void setPaused(boolean paused) { isPaused = paused; }

    public boolean isMapOpen() { return isMapOpen; }
    public void setMapOpen(boolean mapOpen) { isMapOpen = mapOpen; }

    public boolean isAnimatingText() { return isAnimatingText; }
    public void setAnimatingText(boolean animatingText) { isAnimatingText = animatingText; }
}
