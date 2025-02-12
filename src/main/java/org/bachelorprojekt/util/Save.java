package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;

/**
 * Manages the saving of the player's state in the game.
 * The class provides methods to save the current player state into a database.
 */
public class Save {
    
    /**
     * The database used for storing the game state.
     */
    static DB database = new DB("gamestate.db");
    
    /**
     * The current save slot.
     */
    static int slot = 1;

    /**
     * Saves the current state of the player to the database.
     *
     * @param player the player whose state is to be saved
     */
    static void save(Player player) {
        database.add(player);
    }
}
