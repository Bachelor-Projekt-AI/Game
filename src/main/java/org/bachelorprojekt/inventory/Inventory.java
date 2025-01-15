package org.bachelorprojekt.inventory;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * Represents a 4x4 inventory system capable of storing and managing items.
 * Each inventory slot can hold one item. Stackable items can be grouped
 * together up to their maximum stack size.
 */
@Embeddable
public class Inventory implements Serializable {
	@CollectionTable(name = "inventory", joinColumns = @JoinColumn(name = "character_name"))
    private Item[][] items;

    /**
     * Creates a new Inventory with a fixed size of 4x4 (16 slots).
     */
    public Inventory() {
        this.items = new Item[4][4]; // 4x4 Inventory, storage for 16 unique items
    }

    /**
     * Retrieves an item from a specific slot in the inventory.
     *
     * @param x the row index of the item slot.
     * @param y the column index of the item slot.
     * @return the {@link Item} in the specified slot, or {@code null} if the slot is empty.
     * @throws ArrayIndexOutOfBoundsException if the indices are out of bounds.
     */
    public Item getItem(int x, int y) {
        return items[x][y];
    }


    /**
     * Searches for an item in the inventory by its unique ID.
     *
     * @param id the unique identifier of the item to search for.
     * @return the {@link Item} with the specified ID, or {@code null} if not found.
     */
    public Item getItem(int id) {
        for(int i = 0; i < items.length; i++) {
            for(int j = 0; j < items[i].length; j++) {
                if(items[i][j].getId() == id) {
                    return items[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Adds an item to the inventory.
     * If the item is stackable and an existing stack with the same ID exists,
     * it will attempt to add the quantity to that stack. If no suitable stack
     * exists, it will place the item in the first available empty slot.
     *
     * @param id       the unique identifier of the item to add.
     * @param quantity the quantity of the item to add.
     * @return {@code true} if the item was successfully added, {@code false} otherwise.
     */
    public boolean addItem(int id, int quantity) {
        int[] firstFreeSlot = null; // Position of the first free slot
        Item itemToAdd = null; // Reference to the new item if needed

        // Traverse the entire inventory
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j] != null) {
                    // Check if the item is stackable and has the same ID
                    if (items[i][j].getId() == id && items[i][j].isStackable()) {
                        int newQuantity = items[i][j].getQuantity() + quantity;
                        if (newQuantity <= items[i][j].getStackSize()) {
                            // Add the full quantity
                            items[i][j].setQuantity(newQuantity);
                            return true;
                        } else {
                            // Add as much as possible and retain the remaining quantity
                            items[i][j].setQuantity(items[i][j].getStackSize());
                            quantity = newQuantity - items[i][j].getStackSize();
                        }
                    }
                } else if (firstFreeSlot == null) {
                    // Save the position of the first empty slot
                    firstFreeSlot = new int[]{i, j};
                }
            }
        }

        // If there is remaining quantity and a free slot exists
        if (quantity > 0 && firstFreeSlot != null) {
            if (itemToAdd == null) {
                itemToAdd = getItem(id); // Create the new item only if necessary
            }
            itemToAdd.setQuantity(quantity);
            items[firstFreeSlot[0]][firstFreeSlot[1]] = itemToAdd;
            return true;
        }

        // No space in the inventory
        return false;
    }
}
