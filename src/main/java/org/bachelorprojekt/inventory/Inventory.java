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

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryItem> items = new ArrayList<>(); // List of items in the inventory

    /**
     * Default constructor required by Hibernate.
     */
    public Inventory() {}

    /**
     * Adds an item to the inventory. If the item already exists, increases its quantity.
     *
     * @param itemId The ID of the item to add.
     * @param quantity The quantity of the item to add.
     * @return True if the item was added or its quantity was increased.
     */
    public boolean addItem(int itemId, String itemName, int maxStackSize, int quantity) {
        for (InventoryItem item : items) {
            if (item.getItemId() == itemId) {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity <= maxStackSize) {
                    item.setQuantity(newQuantity);
                } else {
                    item.setQuantity(maxStackSize);
                    // Handle overflow if necessary
                    int overflow = newQuantity - maxStackSize;
                    return addItem(itemId, itemName, maxStackSize, overflow);
                }
                return true;
            }
        }

        // Add a new item if it doesn't exist
        InventoryItem newItem = new InventoryItem(itemId, itemName, quantity);
        return items.add(newItem);
    }

    /**
     * Removes an item or reduces its quantity from the inventory.
     *
     * @param itemId The ID of the item to remove.
     * @param quantity The quantity to remove.
     * @return True if the item was removed or its quantity reduced.
     */
    public boolean removeItem(int itemId, int quantity) {
        for (InventoryItem item : items) {
            if (item.getItemId() == itemId) {
                if (item.getQuantity() > quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    return true;
                } else {
                    return items.remove(item);
                }
            }
        }
        return false; // Item not found
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return The InventoryItem if found, or null if not.
     */
    public InventoryItem getItem(int itemId) {
        return items.stream().filter(item -> item.getItemId() == itemId).findFirst().orElse(null);
    }

    /**
     * Gets the list of all items in the inventory.
     *
     * @return The list of items.
     */
    public List<InventoryItem> getItems() {
        return items;
    }

    /**
     * Sets the list of items in the inventory.
     *
     * @param items The list of items to set.
     */
    public void setItems(List<InventoryItem> items) {
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
