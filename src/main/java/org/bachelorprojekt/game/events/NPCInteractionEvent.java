package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Ereignis, das eine Interaktion mit einem NPC (Non-Player Character) darstellt.
 * Dieses Ereignis enthält die ID des NPCs, mit dem der Spieler interagiert hat.
 * 
 * @author [Dein Name]
 * @version 1.0
 */
public class NPCInteractionEvent implements GameEvent {
    private final int npcId;

    /**
     * Konstruktor, der ein NPC-Interaktionsereignis mit der gegebenen NPC-ID erstellt.
     * 
     * @param npcId Die ID des NPCs, mit dem interagiert wurde.
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
