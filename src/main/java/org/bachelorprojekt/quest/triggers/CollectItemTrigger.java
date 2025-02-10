package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.ItemCollectEvent;

public class CollectItemTrigger implements QuestTrigger {

    private final int requiredItemId;
    private final int requiredAmount;
    private int currentAmount = 0;

    public CollectItemTrigger(int requiredItemId, int requiredAmount) {
        this.requiredItemId = requiredItemId;
        this.requiredAmount = requiredAmount;
    }

    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof ItemCollectEvent) {
            ItemCollectEvent itemEvent = (ItemCollectEvent) event;
            if (itemEvent.getItemId() == requiredItemId) {
                currentAmount += itemEvent.getAmount();
                return currentAmount >= requiredAmount;
            }
        }
        return false;
    }

    @Override
    public Class<? extends GameEvent> getEventType() {
        return ItemCollectEvent.class;
    }
}
