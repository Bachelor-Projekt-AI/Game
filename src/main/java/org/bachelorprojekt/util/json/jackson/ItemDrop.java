package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ItemDrop {
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

    public ItemDrop() {
        this.itemId = 0;
        this.dropRate = 0.0;
    }

    public void initMapping(Map<Integer, Item> itemMap) {
        // Find the item with the given ID.
        this.item = itemMap.get(this.itemId);
    }

    private Item item;

    public Item getItem() {
        return item;
    }

}
