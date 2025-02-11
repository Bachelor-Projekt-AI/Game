package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.util.json.jackson.Item;

public class ItemCollectEvent implements GameEvent {

    private final Item item;
    private final int amount;

    public ItemCollectEvent(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

}
