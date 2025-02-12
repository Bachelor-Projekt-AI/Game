package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das ausgelöst wird, wenn ein Spieler nach einem bestimmten Gegenstand sucht.
 * Dieses Ereignis enthält den Namen oder die ID des gesuchten Gegenstands.
 * 
 * @version 1.0
 */
public class ItemSearchEvent implements GameEvent {
    private final String itemName; // oder Item-ID, falls vorhanden

    /**
     * Erstellt ein neues Ereignis für die Suche nach einem Gegenstand.
     * 
     * @param itemName Der Name oder die ID des gesuchten Gegenstands.
     */
    public ItemSearchEvent(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gibt den Namen oder die ID des gesuchten Gegenstands zurück.
     * 
     * @return Der Name oder die ID des gesuchten Gegenstands.
     */
    public String getItemName() {
        return itemName;
    }
}
