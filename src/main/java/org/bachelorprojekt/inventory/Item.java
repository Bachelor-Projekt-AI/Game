package org.bachelorprojekt.inventory;

import jakarta.persistence.*;

// JPA entity linked to the "items" table
@Entity
@Table(name = "items")
public class Item {

    @Column(name = "name", nullable = false) // Item name
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    private int id;

    @Column(name = "quantity", nullable = false) // Quantity of the item
    private int quantity;

    @Column(name = "stackSize", nullable = false) // Maximum items per stack
    private int stackSize;

    @Column(name = "sellPrice", nullable = false) // Selling price of the item
    private int sellPrice;

    @Column(name = "isStackable", nullable = false) // Indicates if the item can stack
    private boolean isStackable;

    @Column(name = "isSellable", nullable = false) // Indicates if the item can be sold
    private boolean isSellable;

    // Constructor with all attributes
    public Item(String name, int quantity, int stackSize, int sellPrice, boolean isStackable, boolean isSellable) {
        this.name = name;
        this.quantity = quantity;
        this.stackSize = stackSize;
        this.sellPrice = sellPrice;
        this.isStackable = isStackable;
        this.isSellable = isSellable;
    }

    // Default constructor required by JPA
    public Item() {}

    // Getter methods
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStackSize() {
        return stackSize;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public boolean isSellable() {
        return isSellable;
    }

    // Setter methods
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the item name
    @Override
    public String toString() {
        return name;
    }
}
