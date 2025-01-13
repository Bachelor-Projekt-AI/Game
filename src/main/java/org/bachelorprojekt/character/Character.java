package org.bachelorprojekt.character;

import org.bachelorprojekt.inventory.Inventory;

public class Character {
    private String name;
    private int level;
    private int experience;
    private int health;
    private int mana;
    private int gold;
    private Inventory inventory;

    public Character(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getGold() {
        return gold;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
