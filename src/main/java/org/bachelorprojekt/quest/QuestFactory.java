package org.bachelorprojekt.quest;

import org.bachelorprojekt.quest.triggers.*;
import org.bachelorprojekt.util.json.jackson.Quest;

/**
 * Erzeugt QuestInstances mit passenden Triggern.
 */
public class QuestFactory {

    public static QuestInstance createQuestInstance(Quest quest) {
        QuestTrigger trigger;

        switch (quest.getQuestType()) {
            case NPC_INTERACTION -> trigger = new NPCInteractionTrigger(quest.getNpcId());
            //case COLLECT_ITEM -> trigger = new CollectItemTrigger(quest.getItemId(), quest.getAmount());
            //case VISIT_LOCATION -> trigger = new VisitLocationTrigger(quest.getLocationId());
            default ->
                throw new IllegalArgumentException("Unbekannter Quest-Typ: " + quest.getQuestType().toString());
        }

        return new QuestInstance(quest, trigger);
    }
}
