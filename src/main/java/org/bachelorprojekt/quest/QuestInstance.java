package org.bachelorprojekt.quest;

import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.quest.triggers.QuestTrigger;
import org.bachelorprojekt.util.json.jackson.Quest;

/**
 * Represents an instance of a quest with associated quest data, progress, and a trigger.
 * A quest instance can track its progress and completion status.
 */
public class QuestInstance {
    private final Quest questData;
    private final QuestTrigger trigger;
    private int progress;
    private boolean completed = false;
    private boolean active = false;

    /**
     * Constructs a new {@link QuestInstance} with the specified quest data and trigger.
     *
     * @param questData The data related to the quest.
     * @param trigger The trigger that activates the quest.
     */
    public QuestInstance(Quest questData, QuestTrigger trigger) {
        this.questData = questData;
        this.trigger = trigger;
        this.progress = 0;
    }

    /**
     * Sets the active status of the quest instance.
     *
     * @param active True to activate the quest instance, false to deactivate it.
     */
    public void setIsActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns whether the quest instance is active.
     *
     * @return True if the quest is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the current progress of the quest instance.
     *
     * @return The progress of the quest instance.
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Sets the current progress of the quest instance.
     *
     * @param progress The progress value to set.
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * Gets the quest data associated with this quest instance.
     *
     * @return The quest data.
     */
    public Quest getQuestData() {
        return questData;
    }

    /**
     * Returns whether the quest instance is completed.
     *
     * @return True if the quest is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the quest instance.
     *
     * @param completed True if the quest is completed, false otherwise.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the trigger associated with this quest instance.
     *
     * @return The trigger that activates the quest instance.
     */
    public QuestTrigger getTrigger() {
        return trigger;
    }
}
