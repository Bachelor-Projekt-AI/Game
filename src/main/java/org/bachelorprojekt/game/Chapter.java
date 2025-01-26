package org.bachelorprojekt.game;

import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Chapter extends ScreenAdapter {
    protected String title;
    protected String description;
    protected List<Story.Quest> quests;
    protected final Engine engine;

    public Chapter(String title, String description, Engine engine) {
        this.title = title;
        this.description = description;
        this.quests = new ArrayList<>();
        this.engine = engine;
    }

    public abstract void start(Player player); // Kapitel starten

    public boolean isCompleted() {
        return quests.stream().allMatch(Story.Quest::getIsCompleted);
    }

    public String getTitle() {
        return title;
    }


}
