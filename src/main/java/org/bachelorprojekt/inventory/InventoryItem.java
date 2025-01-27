package org.bachelorprojekt.inventory;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Represents an item in the inventory, including its quantity and metadata.
 */
@Entity
@Table(name = "inventory_items")
public class InventoryItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Unique identifier for the inventory item

    private int itemId; // Reference to the item ID from the JSON
    private String itemName; // Name of the item (for display purposes)
    private int quantity; // Current quantity of the item

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory; // Reference to the owning inventory

    /**
     * Default constructor required by Hibernate.
     */
    public InventoryItem() {}

    /**
     * Constructs a new InventoryItem.
     *
     * @param itemId   The ID of the item.
     * @param itemName The name of the item.
     * @param quantity The initial quantity of the item.
     */
    public InventoryItem(int itemId, String itemName, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
