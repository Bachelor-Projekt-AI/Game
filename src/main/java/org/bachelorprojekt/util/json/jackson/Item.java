package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private int id;
    private String name;
    private String description;
    private boolean stackable;
    private int maxStackSize;

    public Item() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.stackable = false;
        this.maxStackSize = 0;
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
}
