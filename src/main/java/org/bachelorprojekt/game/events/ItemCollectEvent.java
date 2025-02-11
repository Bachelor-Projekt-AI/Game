package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.util.json.jackson.Item;

/**
 * Repräsentiert ein Spielereignis, bei dem ein Item gesammelt wird.
 */
public class ItemCollectEvent implements GameEvent {

    private final Item item;
    private final int amount;

    /**
     * Erstellt ein neues Item-Sammel-Ereignis.
     *
     * @param item   Das gesammelte Item.
     * @param amount Die Anzahl der gesammelten Items.
     */
    public ItemCollectEvent(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    /**
     * Gibt das gesammelte Item zurück.
     *
     * @return Das Item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gibt die Anzahl der gesammelten Items zurück.
     *
     * @return Die Anzahl der gesammelten Items.
     */
    public int getAmount() {
        return amount;
    }
}
