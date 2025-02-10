package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.NPCInteractionEvent;

public class NPCInteractionTrigger implements QuestTrigger {
    private final int requiredNpcId;

    public NPCInteractionTrigger(int requiredNpcId) {
        this.requiredNpcId = requiredNpcId;
    }

    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof NPCInteractionEvent) {
            NPCInteractionEvent npcEvent = (NPCInteractionEvent) event;
            return npcEvent.getNpcId() == requiredNpcId;
        }
        return false;
    }

    @Override
    public Class<? extends GameEvent> getEventType() {
        return NPCInteractionEvent.class;
    }
}
