package org.bachelorprojekt.character;

import java.util.List;

public class Player {
    private String name;
    private List<String> inventory; // Spielerinventar
    private int currentChapterIndex; // Aktuelles Kapitel
    private int currentQuestIndex;   // Aktuelle Quest im Kapitel

    public Player(String name) {
        this.name = name;
        this.inventory = new java.util.ArrayList<>();
        this.currentChapterIndex = 0; // Start im ersten Kapitel
        this.currentQuestIndex = 0;   // Start mit der ersten Quest
    }

    public String getName() {
        return name;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void addToInventory(String item) {
        inventory.add(item);
    }

    public int getCurrentChapterIndex() {
        return currentChapterIndex;
    }

    public void setCurrentChapterIndex(int currentChapterIndex) {
        this.currentChapterIndex = currentChapterIndex;
    }

    public int getCurrentQuestIndex() {
        return currentQuestIndex;
    }

    public void setCurrentQuestIndex(int currentQuestIndex) {
        this.currentQuestIndex = currentQuestIndex;
    }
}
