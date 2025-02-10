package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

public class ObjectExamineEvent implements GameEvent {
    private final String objectName; // oder Object-ID

    public ObjectExamineEvent(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }
}
