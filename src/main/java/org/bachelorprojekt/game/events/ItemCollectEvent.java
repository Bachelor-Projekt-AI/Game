package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.util.json.jackson.Item;

/**
 * Ein Ereignis, das ausgelöst wird, wenn ein Spieler einen Gegenstand sammelt.
 * Dieses Ereignis speichert Informationen über das gesammelte Item und die Anzahl.
 * 
 * @version 1.0
 */
public class ItemCollectEvent implements GameEvent {

    private final Item item;
    private final int amount;

    /**
     * Erstellt ein neues Ereignis für das Sammeln eines Gegenstands.
     * 
     * @param item   Der gesammelte Gegenstand.
     * @param amount Die Anzahl der gesammelten Gegenstände.
     */
    public ItemCollectEvent(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    /**
     * Gibt das gesammelte Item zurück.
     * 
     * @return Das gesammelte Item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gibt die Anzahl der gesammelten Gegenstände zurück.
     * 
     * @return Die Anzahl der gesammelten Gegenstände.
     */
    public int getAmount() {
        return amount;
    }
}
