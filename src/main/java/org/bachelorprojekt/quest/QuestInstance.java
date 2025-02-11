package org.bachelorprojekt.quest;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.quest.triggers.QuestTrigger;
import org.bachelorprojekt.util.json.jackson.Quest;

public class QuestInstance {
    private final Quest questData;
    private final QuestTrigger trigger;
    private int progress;
    private boolean completed = false;
    private boolean active = false;

    public QuestInstance(Quest questData, QuestTrigger trigger) {
        this.questData = questData;
        this.trigger = trigger;
        this.progress = 0;
    }

    public void setIsActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Quest getQuestData() {
        return questData;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public QuestTrigger getTrigger() {
        return trigger;
    }
}
