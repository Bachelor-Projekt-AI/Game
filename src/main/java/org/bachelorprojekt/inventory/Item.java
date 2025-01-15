package org.bachelorprojekt.inventory;

import jakarta.persistence.*;

@Entity
@Table(name="items")
public class Item {
	@Column(name = "name", nullable = false)
    private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column(name = "quantity", nullable = false)
    private int quantity;

	@Column(name = "stackSize", nullable = false)
    private int stackSize;

	@Column(name = "sellPrice", nullable = false)
    private int sellPrice;

	@Column(name = "isStackable", nullable = false)
    private boolean isStackable;

	@Column(name = "isSellable", nullable = false)
    private boolean isSellable;

    public Item(String name, int quantity, int stackSize, int sellPrice, boolean isStackable, boolean isSellable) {
        this.name = name;
        this.quantity = quantity;
        this.stackSize = stackSize;
        this.sellPrice = sellPrice;
        this.isStackable = isStackable;
        this.isSellable = isSellable;
    }

	public Item() {}

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
