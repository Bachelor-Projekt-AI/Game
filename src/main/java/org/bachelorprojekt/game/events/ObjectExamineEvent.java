package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das eine Untersuchung eines Objekts im Spiel darstellt.
 * Dieses Ereignis enthält den Namen (oder die ID) des Objekts, das untersucht wird.
 * 
 * @author [Dein Name]
 * @version 1.0
 */
public class ObjectExamineEvent implements GameEvent {
    private final String objectName; // oder Object-ID

    /**
     * Konstruktor, der ein Ereignis für die Untersuchung eines Objekts mit dem angegebenen Objektnamen erstellt.
     * 
     * @param objectName Der Name des Objekts, das untersucht wird.
     */
    public ObjectExamineEvent(String objectName) {
        this.objectName = objectName;
    }

    /**
     * Gibt den Namen des Objekts zurück, das untersucht wird.
     * 
     * @return Der Name des Objekts.
     */
    public String getObjectName() {
        return objectName;
    }
}
