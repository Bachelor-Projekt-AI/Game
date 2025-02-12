package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.LocationReachEvent;

/**
 * A trigger that is activated when a specific location is reached.
 */
public class VisitLocationTrigger implements QuestTrigger {
    private final int targetLocationId;

    /**
     * Constructs a VisitLocationTrigger with the specified target location ID.
     *
     * @param targetLocationId The ID of the location that must be reached to trigger the event.
     */
    public VisitLocationTrigger(int targetLocationId) {
        this.targetLocationId = targetLocationId;
    }

    /**
     * Checks if the trigger has been activated based on the given event.
     * The trigger is activated when a {@link LocationReachEvent} with the specified location ID is fired.
     *
     * @param event The game event to check.
     * @return True if the event matches the criteria and activates the trigger, false otherwise.
     */
    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof LocationReachEvent) {
            return ((LocationReachEvent) event).getLocationId() == targetLocationId;
        }
        return false;
    }

    /**
     * Retrieves the event type that triggers this quest trigger.
     *
     * @return The class type of the event this trigger responds to, {@link LocationReachEvent}.
     */
    @Override
    public Class<? extends GameEvent> getEventType() {
        return LocationReachEvent.class;
    }
}

