package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Enemy {
    private int id;
    private String name;

    @JsonProperty("dialogues")
    private List<String> dialogues;

    @JsonProperty("max_health")
    private int maxHealth;

    @JsonProperty("base_damage")
    private int baseDamage;

    @JsonProperty("bonus_damage")
    private int bonusDamage;

    @JsonProperty("location_id")
    private int locationId;

    @JsonProperty("loot_table")
    private List<LootEntry> lootTable;

    // Mapped Objects
    private Location location;
    private List<Item> lootItems;

    @JsonProperty("heal_rate")
    private double healRate;

    @JsonProperty("heal_amount")
    private int healAmount;

    private int health;
    private static final Random RANDOM = new Random();

    public Enemy() {
        this.id = 0;
        this.name = "";
        this.dialogues = List.of();  // Initialisiere als leere Liste, um NullPointer zu vermeiden
        this.maxHealth = 0;
        this.baseDamage = 0;
        this.bonusDamage = 0;
        this.locationId = 0;
        this.lootTable = List.of();
        this.healRate = 0;
        this.healAmount = 0;
        this.health = maxHealth; // Start-Gesundheit auf Max setzen
    }

    // ============================= GETTER =============================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBonusDamage() {
        return bonusDamage;
    }

    public int getLocationId() {
        return locationId;
    }

    public List<LootEntry> getLootTable() {
        return lootTable;
    }

    public Location getLocation() {
        return location;
    }

    public List<Item> getLootItems() {
        return lootItems;
    }

    public double getHealRate() {
        return healRate;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public List<String> getDialogues() {
        return dialogues;
    }

    // ============================= MAPPING =============================
    public void initMapping(Map<Integer, Location> locationMap, Map<Integer, Item> itemMap) {
        this.location = locationMap.get(this.locationId);
        this.health = maxHealth;
        this.lootItems = lootTable.stream()
                .map(lootEntry -> itemMap.get(lootEntry.getItemId()))
                .filter(item -> item != null)
                .toList();
    }

    // ============================= METHODEN =============================

    /**
     * Gibt einen zufälligen Dialog des Gegners zurück.
     * Falls keine Dialoge vorhanden sind, wird eine Standardnachricht zurückgegeben.
     */
    public String getRandomDialogue() {
        if (dialogues == null || dialogues.isEmpty()) {
            return name + " knurrt dich feindselig an!";
        }
        return dialogues.get(RANDOM.nextInt(dialogues.size()));
    }

    // ============================= INNER CLASS =============================
    public static class LootEntry {
        @JsonProperty("item_id")
        private int itemId;
        @JsonProperty("drop_rate")
        private double dropRate;

        public int getItemId() {
            return itemId;
        }

        public double getDropRate() {
            return dropRate;
        }
    }

    // ========================== Setter for internal combat ===================

    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void heal(int healAmount) {
        this.health += healAmount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health == 0;
    }
}
