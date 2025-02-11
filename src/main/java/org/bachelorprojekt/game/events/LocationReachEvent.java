package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

public class LocationReachEvent implements GameEvent {

    private final int locationId;

    public LocationReachEvent(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }
}
