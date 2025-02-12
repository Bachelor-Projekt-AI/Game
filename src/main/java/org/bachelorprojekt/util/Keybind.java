package org.bachelorprojekt.util;

import com.badlogic.gdx.Input.Keys;

/**
 * Represents a keybind in the game, associating a key press with a description and an action.
 * The keybind includes functionality to format the key name and provide its full description.
 */
public class Keybind {
    
    private final int key;
    private final String fullDescription;
    private final Runnable action;
    private final int PADDED = 5;

    /**
     * Constructs a Keybind object with the specified key, description, and action.
     * 
     * @param key the key associated with the keybind
     * @param description the description of the keybind action
     * @param action the action to execute when the key is pressed
     */
    public Keybind(int key, String description, Runnable action) {
        this.key = key;
        this.fullDescription = keyString(key) + description;
        this.action = action;
    }

    /**
     * Converts the key code into a formatted string representing the key.
     * 
     * @param key the key code to convert
     * @return a formatted string containing the key name and padding
     */
    private String keyString(int key) {
        String name = keyName(key);
        String padding = "";
        for (int i = 0; i < PADDED - name.length(); i++) {
            padding += " ";
        }
        return padding + "(" + name + ") ";
    }

    /**
     * Returns the name of the key as a string based on its key code.
     * 
     * @param key the key code to convert
     * @return the name of the key (e.g., "ESC", "UP", etc.)
     */
    private String keyName(int key) {
        switch (key) {
            case Keys.ESCAPE: return "ESC";
            case Keys.UP: return "UP";
            case Keys.DOWN: return "DOWN";
            case Keys.LEFT: return "LEFT";
            case Keys.RIGHT: return "RIGHT";
            case Keys.ENTER: return "ENTER";
            case Keys.BACKSPACE: return "BACK";
            case Keys.C: return "C";
            case Keys.E: return "E";
            case Keys.K: return "K";
            case Keys.M: return "M";
            case Keys.Q: return "Q";
            default: return "";
        }
    }

    /**
     * Returns the key associated with the keybind.
     * 
     * @return the key code of the keybind
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns the full description of the keybind, including the key name and the action description.
     * 
     * @return the full description of the keybind
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Returns the action associated with the keybind.
     * 
     * @return the action to execute when the key is pressed
     */
    public Runnable getAction() {
        return action;
    }
}
