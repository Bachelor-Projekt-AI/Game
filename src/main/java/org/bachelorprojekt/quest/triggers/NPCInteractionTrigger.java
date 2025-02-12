package org.bachelorprojekt.quest.triggers;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.NPCInteractionEvent;

/**
 * A trigger that is activated when a specific NPC is interacted with.
 */
public class NPCInteractionTrigger implements QuestTrigger {
    private final int requiredNpcId;

    /**
     * Constructs an NPCInteractionTrigger with the specified NPC ID.
     *
     * @param requiredNpcId The ID of the NPC that must be interacted with to trigger the event.
     */
    public NPCInteractionTrigger(int requiredNpcId) {
        this.requiredNpcId = requiredNpcId;
    }

    /**
     * Checks if the trigger has been activated based on the given event.
     * The trigger is activated when an {@link NPCInteractionEvent} with the specified NPC ID is fired.
     *
     * @param event The game event to check.
     * @return True if the event matches the criteria and activates the trigger, false otherwise.
     */
    @Override
    public boolean isTriggered(GameEvent event) {
        if (event instanceof NPCInteractionEvent) {
            NPCInteractionEvent npcEvent = (NPCInteractionEvent) event;
            return npcEvent.getNpcId() == requiredNpcId;
        }
        return false;
    }

    /**
     * Retrieves the event type that triggers this quest trigger.
     *
     * @return The class type of the event this trigger responds to, {@link NPCInteractionEvent}.
     */
    @Override
    public Class<? extends GameEvent> getEventType() {
        return NPCInteractionEvent.class;
    }
}
