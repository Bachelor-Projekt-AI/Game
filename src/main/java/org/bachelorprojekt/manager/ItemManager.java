package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the items in the application, providing methods to retrieve items by ID,
 * check if an item exists, and get all items.
 */
public class ItemManager {

    private final Map<Integer, Item> items;

    /**
     * Constructs an ItemManager instance, initializing the items from the provided list.
     * The items are mapped by their ID.
     *
     * @param itemList A list of items to be managed.
     */
    public ItemManager(List<Item> itemList) {
        this.items = new HashMap<>();
        for (Item item : itemList) {
            items.put(item.getId(), item);
        }
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return The item corresponding to the given ID, or null if not found.
     */
    public Item getItemById(int itemId) {
        return items.get(itemId);
    }

    /**
     * Retrieves all items managed by this ItemManager.
     *
     * @return A list of all items.
     */
    public List<Item> getAllItems() {
        return List.copyOf(items.values());
    }

    /**
     * Checks if an item with the given ID exists.
     *
     * @param itemId The ID of the item to check.
     * @return True if an item with the given ID exists, false otherwise.
     */
    public boolean itemExists(int itemId) {
        return items.containsKey(itemId);
    }
}
