package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

public class ItemSearchEvent implements GameEvent {
    private final String itemName; // oder Item-ID, falls vorhanden

    public ItemSearchEvent(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
