package org.bachelorprojekt.character;

import java.util.List;

public class Player {
    private String name;
    private List<String> inventory;

    public Player(String name) {
        this.name = name;
        this.inventory = new java.util.ArrayList<>();
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
}
