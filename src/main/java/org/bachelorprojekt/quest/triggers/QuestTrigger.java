package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;

public interface QuestTrigger {
    /**
     * Prüft, ob das übergebene Event diesen Trigger auslöst.
     *
     * @param event Das eingetretene Spielereignis.
     * @return true, wenn der Trigger ausgelöst wurde, ansonsten false.
     */
    boolean isTriggered(GameEvent event);

    Class<? extends GameEvent> getEventType();
}
