package org.bachelorprojekt.character;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private List<Item> inventory;
    private Location location;
    private int maxHealth;
    private int health;

    public Player(String name, int health, int maxHealth) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.health = health;
        this.maxHealth = maxHealth;
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

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void heal(int health) {
        System.out.println("Healing " + health + " health points.");
        System.out.println("Current health: " + this.health);
        System.out.println("Max health: " + maxHealth);
        this.health += health;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }

        System.out.println("New health: " + this.health);
    }

    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void revive() {
        this.health = maxHealth;
    }

    public boolean isDead() {
        return health == 0;
    }
}
