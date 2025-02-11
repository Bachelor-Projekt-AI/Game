package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Enemy {
    private int id;
    private String name;

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

    public Enemy() {
        this.id = 0;
        this.name = "";
        this.maxHealth = 0;
        this.baseDamage = 0;
        this.bonusDamage = 0;
        this.locationId = 0;
        this.lootTable = List.of();
        this.healRate = 0;
        this.healAmount = 0;
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

    // ============================= MAPPING =============================
    public void initMapping(Map<Integer, Location> locationMap, Map<Integer, Item> itemMap) {
        this.location = locationMap.get(this.locationId);
        this.health = maxHealth;
        // Mappe Items aus der Loot-Tabelle
        this.lootItems = lootTable.stream()
                .map(lootEntry -> itemMap.get(lootEntry.getItemId()))
                .filter(item -> item != null)
                .toList();
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

    private int health;

    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void heal(int healAmount) {
        this.health += healAmount;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health == 0;
    }

}
