package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

enum ItemType {
    UNKNOWN,
    WEAPON,
    CONSUMABLE,
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private int id;
    private String name;
    private String description;
    private boolean stackable;
    private int maxStackSize;
    private String rarity;

    @JsonProperty("sell_price")
    private int sellPrice;

    @JsonProperty("buy_price")
    private int buyPrice;

    private String category;

    @JsonProperty("gives_hp")
    private boolean givesHp;

    private int health;

    @JsonProperty("gives_mana")
    private boolean givesMana;

    private int mana;

    @JsonProperty("does_damage")
    private boolean doesDamage;

    private int damage;
    /*
    * "rarity": "rare",
      "sell_price": 100,
      "buy_price": 1000,
      "category": "weapon"
    *
    * */

    public Item() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.stackable = false;
        this.maxStackSize = 0;
        this.rarity = "";
        this.sellPrice = 0;
        this.buyPrice = 0;
        this.category = "";
        this.givesHp = false;
        this.health = 0;
        this.givesMana = false;
        this.mana = 0;
        this.doesDamage = false;
        this.damage = 0;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getRarity() {
        return rarity;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public String getCategory() {
        return category;
    }

    public boolean givesHp() {
        return givesHp;
    }

    public int getHealth() {
        return health;
    }

    public boolean givesMana() {
        return givesMana;
    }

    public int getMana() {
        return mana;
    }

    public boolean doesDamage() {
        return doesDamage;
    }

    public int getDamage() {
        return damage;
    }

    public ItemType getItemType() {
        return ItemType.valueOf(category);
    }
}
