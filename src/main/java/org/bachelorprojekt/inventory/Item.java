package org.bachelorprojekt.inventory;

public class Item {
    private String name;
    private int id;
    private int quantity;
    private int stackSize;
    private int sellPrice;
    private boolean isStackable;
    private boolean isSellable;

    public Item(String name, int id, int quantity, int stackSize, int sellPrice, boolean isStackable, boolean isSellable) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.stackSize = stackSize;
        this.sellPrice = sellPrice;
        this.isStackable = isStackable;
        this.isSellable = isSellable;
    }

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
