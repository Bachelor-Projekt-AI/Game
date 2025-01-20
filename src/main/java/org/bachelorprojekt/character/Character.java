package org.bachelorprojekt.character;

import org.bachelorprojekt.inventory.Inventory;
import jakarta.persistence.*;

// JPA entity linked to the "character" table
@Entity
@Table(name = "character")
public class Character {

    // Primary key of the table
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "level") // Character level
    private int level;

    @Column(name = "experience") // Experience points
    private int experience;

    @Column(name = "health") // Health points
    private int health;

    @Column(name = "mana") // Mana points
    private int mana;

    @Column(name = "gold") // Amount of gold
    private int gold;

    @Column(name = "inventory") // Character inventory
    private Inventory inventory;

    // Constructor with default values
    public Character(String name) {
        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.health = 15;
        this.mana = 15;
        this.gold = 5;
        this.inventory = new Inventory();
    }

    // Default constructor required by JPA
    public Character() {}

    // Returns the character's name
    @Override
    public String toString() {
        return name;
    }

    // Getter methods
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

    // Setter methods
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
