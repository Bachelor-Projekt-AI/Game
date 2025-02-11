package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Repräsentiert ein Spielereignis, bei dem ein Spieler nach einem bestimmten Item sucht.
 */
public class ItemSearchEvent implements GameEvent {
    private final String itemName; // oder Item-ID, falls vorhanden

    /**
     * Erstellt ein neues Item-Suche-Ereignis.
     *
     * @param itemName Der Name (oder die ID) des gesuchten Items.
     */
    public ItemSearchEvent(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gibt den Namen (oder die ID) des gesuchten Items zurück.
     *
     * @return Der Name des gesuchten Items.
     */
    public String getItemName() {
        return itemName;
    }
}
