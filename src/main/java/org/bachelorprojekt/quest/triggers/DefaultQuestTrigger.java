package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;

/**
 * A default quest trigger that is always triggered for any game event.
 */
public class DefaultQuestTrigger implements QuestTrigger {

    /**
     * Always returns true, indicating that the trigger is always activated.
     *
     * @param event The game event to check.
     * @return Always true, indicating the trigger is always activated.
     */
    @Override
    public boolean isTriggered(GameEvent event) {
        return true;
    }

    /**
     * Retrieves the event type that triggers this quest trigger.
     * In this case, it returns {@link GameEvent} as the trigger is always activated for any event.
     *
     * @return The class type of the event this trigger responds to, {@link GameEvent}.
     */
    @Override
    public Class<? extends GameEvent> getEventType() {
        return GameEvent.class;
    }
}
