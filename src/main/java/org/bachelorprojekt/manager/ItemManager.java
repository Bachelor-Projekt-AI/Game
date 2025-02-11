package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private final Map<Integer, Item> items;

    public ItemManager(List<Item> itemList) {
        this.items = new HashMap<>();
        for (Item item : itemList) {
            items.put(item.getId(), item);
        }
    }

    public Item getItemById(int itemId) {
        return items.get(itemId);
    }

    public List<Item> getAllItems() {
        return List.copyOf(items.values());
    }

    public boolean itemExists(int itemId) {
        return items.containsKey(itemId);
    }
}
