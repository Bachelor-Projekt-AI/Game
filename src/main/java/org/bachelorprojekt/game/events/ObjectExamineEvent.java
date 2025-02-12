package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das ausgelöst wird, wenn ein Spieler ein Objekt im Spiel untersucht.
 * Dieses Ereignis enthält den Namen oder die ID des untersuchten Objekts.
 * 
 * @version 1.0
 */
public class ObjectExamineEvent implements GameEvent {
    private final String objectName; // oder Object-ID

    /**
     * Erstellt ein neues Ereignis für die Untersuchung eines Objekts.
     * 
     * @param objectName Der Name oder die ID des untersuchten Objekts.
     */
    public ObjectExamineEvent(String objectName) {
        this.objectName = objectName;
    }

    /**
     * Gibt den Namen oder die ID des untersuchten Objekts zurück.
     * 
     * @return Der Name oder die ID des Objekts.
     */
    public String getObjectName() {
        return objectName;
    }
}
