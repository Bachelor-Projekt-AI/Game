package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das ausgelöst wird, wenn ein Spieler mit einem NPC interagiert.
 * Dieses Ereignis enthält die ID des NPCs, mit dem die Interaktion stattfindet.
 * 
 * @version 1.0
 */
public class NPCInteractionEvent implements GameEvent {
    private final int npcId;

    /**
     * Erstellt ein neues Ereignis für die Interaktion mit einem NPC.
     * 
     * @param npcId Die ID des NPCs, mit dem interagiert wird.
     */
    public NPCInteractionEvent(int npcId) {
        this.npcId = npcId;
    }

    /**
     * Gibt die ID des NPCs zurück, mit dem interagiert wurde.
     * 
     * @return Die ID des NPCs.
     */
    public int getNpcId() {
        return npcId;
    }
}
