package org.bachelorprojekt.quest;

import org.bachelorprojekt.quest.triggers.*;
import org.bachelorprojekt.util.json.jackson.Quest;

/**
 * A factory class that creates {@link QuestInstance} objects with the appropriate triggers based on the quest type.
 */
public class QuestFactory {

    /**
     * Creates a {@link QuestInstance} based on the provided quest.
     * The appropriate trigger is selected based on the quest type, such as NPC interaction, item collection,
     * or location visit.
     *
     * @param quest The quest object that defines the type and requirements of the quest.
     * @return A new {@link QuestInstance} with the corresponding trigger.
     * @throws IllegalArgumentException If the quest type is unknown.
     */
    public static QuestInstance createQuestInstance(Quest quest) {
        QuestTrigger trigger;

        switch (quest.getQuestType()) {
            case NPC_INTERACTION -> trigger = new NPCInteractionTrigger(quest.getNpcId());
            case COLLECT_ITEM -> trigger = new CollectItemTrigger(quest.getRequiredItemId(), quest.getRequiredAmount());
            case VISIT_LOCATION -> trigger = new VisitLocationTrigger(quest.getTargetLocationId());
            default ->
                throw new IllegalArgumentException("Unbekannter Quest-Typ: " + quest.getQuestType().toString());
        }

        return new QuestInstance(quest, trigger);
    }
}
