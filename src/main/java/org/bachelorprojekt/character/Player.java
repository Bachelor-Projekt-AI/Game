package org.bachelorprojekt.character;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Item> inventory;
    private Location location;
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;

    public Player(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public void removeFromInventory(int index) {
        inventory.remove(index);
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    /* Active quests */


    public List<Quest> getActiveQuests() {
        return activeQuests;
    }

    public void addActiveQuest(Quest quest) {
        activeQuests.add(quest);
    }

    public void removeActiveQuest(Quest quest) {
        activeQuests.remove(quest);
    }

    public void removeActiveQuest(int index) {
        activeQuests.remove(index);
    }

    public void setActiveQuests(List<Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }


    /* Completed quests */

    public List<Quest> getCompletedQuests() {
        return completedQuests;
    }

    public void addCompletedQuest(Quest quest) {
        completedQuests.add(quest);
    }

    public void removeCompletedQuest(Quest quest) {
        completedQuests.remove(quest);
    }

    public void removeCompletedQuest(int index) {
        completedQuests.remove(index);
    }

    public void setCompletedQuests(List<Quest> completedQuests) {
        this.completedQuests = completedQuests;
    }
}
