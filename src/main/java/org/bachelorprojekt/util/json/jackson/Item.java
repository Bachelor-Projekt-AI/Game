package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
