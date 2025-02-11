package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Repräsentiert ein Spielereignis, das ausgelöst wird, wenn ein Spieler eine bestimmte Location erreicht.
 */
public class LocationReachEvent implements GameEvent {

    private final int locationId;

    /**
     * Erstellt ein neues Location-Erreichen-Ereignis.
     *
     * @param locationId Die ID der erreichten Location.
     */
    public LocationReachEvent(int locationId) {
        this.locationId = locationId;
    }

    /**
     * Gibt die ID der erreichten Location zurück.
     *
     * @return Die ID der Location.
     */
    public int getLocationId() {
        return locationId;
    }
}
