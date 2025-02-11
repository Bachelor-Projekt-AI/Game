package org.bachelorprojekt.character;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Player repräsentiert den spielbaren Charakter im Spiel.
 * Sie enthält Informationen zu Gesundheit, Inventar, Standort und ausgerüsteten Gegenständen.
 */
public class Player {
    private final String name;
    private List<Item> inventory;
    private Location location;
    private int maxHealth;
    private int health;
    private int gold;
    private Item head;
    private Item body;
    private Item arms;
    private Item ring;
    private Item feet;

    /**
     * Erstellt einen neuen Spieler mit den angegebenen Attributen.
     *
     * @param name      Der Name des Spielers.
     * @param health    Die aktuelle Gesundheit des Spielers.
     * @param maxHealth Die maximale Gesundheit des Spielers.
     * @param gold      Die Menge an Gold, die der Spieler besitzt.
     */
    public Player(String name, int health, int maxHealth, int gold) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.health = health;
        this.maxHealth = maxHealth;
        this.gold = gold;
    }

    /**
     * Gibt den Namen des Spielers zurück.
     *
     * @return Der Name des Spielers.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt das Inventar des Spielers zurück.
     *
     * @return Eine Liste der im Inventar befindlichen Gegenstände.
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Fügt einen Gegenstand zum Inventar hinzu.
     *
     * @param item Der hinzuzufügende Gegenstand.
     */
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Entfernt einen bestimmten Gegenstand aus dem Inventar.
     *
     * @param item Der zu entfernende Gegenstand.
     */
    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    /**
     * Entfernt einen Gegenstand anhand seines Indexes aus dem Inventar.
     *
     * @param index Der Index des zu entfernenden Gegenstands.
     */
    public void removeFromInventory(int index) {
        inventory.remove(index);
    }

    /**
     * Setzt das Inventar des Spielers.
     *
     * @param inventory Die neue Inventarliste.
     */
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Gibt den aktuellen Standort des Spielers zurück.
     *
     * @return Der Standort des Spielers.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Setzt den aktuellen Standort des Spielers.
     *
     * @param location Der neue Standort.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gibt die aktuelle Gesundheit des Spielers zurück.
     *
     * @return Die aktuelle Gesundheit.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gibt die maximale Gesundheit des Spielers zurück.
     *
     * @return Die maximale Gesundheit.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Heilt den Spieler um eine bestimmte Anzahl von Lebenspunkten.
     *
     * @param health Die Menge an Heilung.
     */
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

    /**
     * Fügt dem Spieler Schaden zu.
     *
     * @param damage Die Menge an Schaden.
     */
    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Belebt den Spieler mit voller Gesundheit wieder.
     */
    public void revive() {
        this.health = maxHealth;
    }

    /**
     * Überprüft, ob der Spieler tot ist.
     *
     * @return {@code true}, wenn die Gesundheit 0 ist, sonst {@code false}.
     */
    public boolean isDead() {
        return health == 0;
    }

    /**
     * Setzt die Menge an Gold, die der Spieler besitzt.
     *
     * @param gold Die neue Goldmenge.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Gibt die aktuelle Goldmenge des Spielers zurück.
     *
     * @return Die Goldmenge des Spielers.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Gibt den aktuell ausgerüsteten Helm zurück.
     *
     * @return Das ausgerüstete Kopf-Item.
     */
    public Item getHead() {
        return head;
    }

    /**
     * Rüstet ein Item als Helm aus.
     *
     * @param head Das auszurüstende Kopf-Item.
     */
    public void setHead(Item head) {
        this.head = head;
    }

    /**
     * Gibt die aktuell ausgerüstete Rüstung zurück.
     *
     * @return Das ausgerüstete Körper-Item.
     */
    public Item getBody() {
        return body;
    }

    /**
     * Rüstet ein Item als Körperrüstung aus.
     *
     * @param body Das auszurüstende Körper-Item.
     */
    public void setBody(Item body) {
        this.body = body;
    }

    /**
     * Gibt die aktuell ausgerüsteten Arm-Gegenstände zurück.
     *
     * @return Das ausgerüstete Arm-Item.
     */
    public Item getArms() {
        return arms;
    }

    /**
     * Rüstet ein Item für die Arme aus.
     *
     * @param arms Das auszurüstende Arm-Item.
     */
    public void setArms(Item arms) {
        this.arms = arms;
    }

    /**
     * Gibt den aktuell ausgerüsteten Ring zurück.
     *
     * @return Das ausgerüstete Ring-Item.
     */
    public Item getRing() {
        return ring;
    }

    /**
     * Rüstet ein Item als Ring aus.
     *
     * @param ring Das auszurüstende Ring-Item.
     */
    public void setRing(Item ring) {
        this.ring = ring;
    }

    /**
     * Gibt die aktuell ausgerüsteten Schuhe zurück.
     *
     * @return Das ausgerüstete Fuß-Item.
     */
    public Item getFeet() {
        return feet;
    }

    /**
     * Rüstet ein Item für die Füße aus.
     *
     * @param feet Das auszurüstende Fuß-Item.
     */
    public void setFeet(Item feet) {
        this.feet = feet;
    }
}
