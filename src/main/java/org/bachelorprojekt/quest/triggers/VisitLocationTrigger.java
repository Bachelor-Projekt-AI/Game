package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.LocationReachEvent;

public class VisitLocationTrigger implements QuestTrigger {
    private final int targetLocationId;

    public VisitLocationTrigger(int targetLocationId) {
        this.targetLocationId = targetLocationId;
    }

    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof LocationReachEvent) {
            return ((LocationReachEvent) event).getLocationId() == targetLocationId;
        }
        return false;
    }

    @Override
    public Class<? extends GameEvent> getEventType() {
        return LocationReachEvent.class;
    }
}
