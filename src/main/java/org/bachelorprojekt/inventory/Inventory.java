package org.bachelorprojekt.inventory;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the inventory of a player, storing a dynamic list of items.
 * This class is mapped to a database table using Hibernate.
 */
@Entity
@Table(name = "inventories")
public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Unique identifier for the inventory

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>(); // List of items in the inventory

    /**
     * Default constructor required by Hibernate.
     */
    public Inventory() {}

    /**
     * Adds an item to the inventory. If the item is stackable and already exists, increases its quantity.
     *
     * @param newItem The item to add.
     * @return True if the item was added or stacked, false if the inventory is full.
     */
    public boolean addItem(Item newItem) {
        for (Item item : items) {
            if (item.getId() == newItem.getId() && item.isStackable()) {
                int newQuantity = item.getQuantity() + newItem.getQuantity();
                if (newQuantity <= item.getStackSize()) {
                    item.setQuantity(newQuantity);
                } else {
                    item.setQuantity(item.getStackSize());
                    newItem.setQuantity(newQuantity - item.getStackSize());
                    return addItem(newItem); // Add the remainder as a new item
                }
                return true;
            }
        }

        // Add as a new item if not stackable or not found
        return items.add(newItem);
    }

    /**
     * Removes an item from the inventory.
     *
     * @param itemId The ID of the item to remove.
     * @return True if the item was removed, false if it was not found.
     */
    public boolean removeItem(int itemId) {
        return items.removeIf(item -> item.getId() == itemId);
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return The item if found, or null if not.
     */
    public Item getItem(int itemId) {
        return items.stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
    }

    /**
     * Gets the list of all items in the inventory.
     *
     * @return The list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the list of items in the inventory.
     *
     * @param items The list of items to set.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Gets the ID of the inventory.
     *
     * @return The inventory ID.
     */
    public int getId() {
        return id;
    }

}
