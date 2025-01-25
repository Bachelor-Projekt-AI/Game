package org.bachelorprojekt.game;

import org.bachelorprojekt.character.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Chapter {
    protected String title;
    protected String description;
    protected List<Story.Quest> quests;

    public Chapter(String title, String description) {
        this.title = title;
        this.description = description;
        this.quests = new ArrayList<>();
    }

    public abstract void start(Player player); // Kapitel starten

    public boolean isCompleted() {
        return quests.stream().allMatch(Story.Quest::getIsCompleted);
    }

    public String getTitle() {
        return title;
    }
}
