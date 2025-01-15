package org.bachelorprojekt.character;

import org.bachelorprojekt.inventory.Inventory;
import jakarta.persistence.*;

@Entity
@Table(name = "character")
public class Character {
	@Id
	@Column(name = "name")
    private String name;

	@Column(name = "level")
    private int level;

	@Column(name = "experience")
    private int experience;

	@Column(name = "health")
    private int health;

	@Column(name = "mana")
    private int mana;

	@Column(name = "gold")
    private int gold;

	@Column(name = "inventory")
    private Inventory inventory;

    public Character(String name) {
        this.name = name;
		this.level = 1;
		this.experience = 0;
		this.health = 15;
		this.mana = 15;
		this.gold = 5;
		this.inventory = new Inventory();
    }

	public Character() {}

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
