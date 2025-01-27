package org.bachelorprojekt.character;

import org.bachelorprojekt.util.json.jackson.Location;

import java.util.List;

public class Player {
    private String name;
    private List<String> inventory;
    private Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
