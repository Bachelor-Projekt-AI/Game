package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;

public class DefaultQuestTrigger implements QuestTrigger {

    @Override
    public boolean isTriggered(GameEvent event) {
        return true;
    }

    @Override
    public Class<? extends GameEvent> getEventType() {
        return GameEvent.class;
    }
}
