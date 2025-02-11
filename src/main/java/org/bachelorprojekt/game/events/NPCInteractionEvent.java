package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

public class NPCInteractionEvent implements GameEvent {
    private final int npcId;

    public NPCInteractionEvent(int npcId) {
        this.npcId = npcId;
    }

    public int getNpcId() {
        return npcId;
    }
}
