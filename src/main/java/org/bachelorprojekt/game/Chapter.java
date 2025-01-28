package org.bachelorprojekt.game;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Chapter extends Scene {
    protected String title;
    protected String description;
    protected List<Story.Quest> quests;

    public Chapter(String title, String description, Engine engine) {
        super(engine);
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
