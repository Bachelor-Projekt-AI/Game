package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.ItemCollectEvent;

/**
 * A trigger that is activated when a specific item is collected a required number of times.
 */
public class CollectItemTrigger implements QuestTrigger {

    private final int requiredItemId;
    private final int requiredAmount;
    private int currentAmount = 0;

    /**
     * Constructs a CollectItemTrigger with the specified item ID and required amount.
     *
     * @param requiredItemId The ID of the item that must be collected.
     * @param requiredAmount The number of items that must be collected to trigger the event.
     */
    public CollectItemTrigger(int requiredItemId, int requiredAmount) {
        this.requiredItemId = requiredItemId;
        this.requiredAmount = requiredAmount;
    }

    /**
     * Checks if the trigger has been activated based on the given event.
     * The trigger is activated when an {@link ItemCollectEvent} matching the required item ID is fired.
     *
     * @param event The game event to check.
     * @return True if the event matches the criteria and activates the trigger, false otherwise.
     */
    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof ItemCollectEvent) {
            ItemCollectEvent itemEvent = (ItemCollectEvent) event;
            if (itemEvent.getItem().getId() == requiredItemId) {
                currentAmount += itemEvent.getAmount();
                return currentAmount >= requiredAmount;
            }
        }
        return false;
    }

    /**
     * Retrieves the event type that triggers this quest trigger.
     *
     * @return The class type of the event this trigger responds to.
     */
    @Override
    public Class<? extends GameEvent> getEventType() {
        return ItemCollectEvent.class;
    }
}
