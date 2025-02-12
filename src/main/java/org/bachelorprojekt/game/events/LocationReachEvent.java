package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das ausgelöst wird, wenn ein Spieler eine bestimmte Location erreicht.
 * Dieses Ereignis enthält die ID der erreichten Location.
 * 
 * @version 1.0
 */
public class LocationReachEvent implements GameEvent {

    private final int locationId;

    /**
     * Erstellt ein neues Ereignis für das Erreichen einer Location.
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
