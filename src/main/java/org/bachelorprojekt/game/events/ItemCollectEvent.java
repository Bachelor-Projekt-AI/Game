package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

public class ItemCollectEvent implements GameEvent {

    private final int itemId;
    private final int amount;

    public ItemCollectEvent(int itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

}
