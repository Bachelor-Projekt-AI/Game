package org.bachelorprojekt.quest;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.quest.triggers.QuestTrigger;
import org.bachelorprojekt.util.json.jackson.Quest;

public class QuestInstance {
    private final Quest questData;
    private final QuestTrigger trigger;
    private boolean completed = false;

    public QuestInstance(Quest questData, QuestTrigger trigger) {
        this.questData = questData;
        this.trigger = trigger;
    }

    public void handleEvent(GameEvent event) {
        if (!completed && trigger.isTriggered(event)) {
            complete();
        }
    }

    public void complete() {
        this.completed = true;
        System.out.println("Quest abgeschlossen: " + questData.getTitle());
    }

    public Quest getQuestData() {
        return questData;
    }

    public boolean isCompleted() {
        return completed;
    }

    public QuestTrigger getTrigger() {
        return trigger;
    }
}
