package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;

public class Save {
	static DB database = new DB("gamestate.db");
	static int slot = 1;

	static void save(Player player) {
		database.add(player);
	}
}
