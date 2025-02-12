package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Enemy;
import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the enemies in the application, providing methods to retrieve enemies by ID,
 * check if an enemy exists, and get all enemies.
 */
public class EnemyManager {
    private final Map<Integer, Enemy> enemies;

    /**
     * Constructs an EnemyManager instance, initializing the enemies from the provided lists.
     * The enemies are mapped by their ID, and each enemy is initialized with corresponding
     * location and item data.
     *
     * @param enemyList A list of enemies to be managed.
     * @param locationList A list of locations to be associated with the enemies.
     * @param itemList A list of items to be associated with the enemies.
     */
    public EnemyManager(List<Enemy> enemyList, List<Location> locationList, List<Item> itemList) {
        this.enemies = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, Item> itemMap = itemList.stream().collect(Collectors.toMap(Item::getId, npc -> npc));

        for (Enemy enemy : enemyList) {
            enemy.initMapping(locationMap, itemMap);
            enemies.put(enemy.getId(), enemy);
        }
    }

    /**
     * Retrieves an enemy by its ID.
     *
     * @param enemyId The ID of the enemy to retrieve.
     * @return The enemy corresponding to the given ID, or null if not found.
     */
    public Enemy getEnemyById(int enemyId) {
        return enemies.get(enemyId);
    }

    /**
     * Retrieves all enemies managed by this EnemyManager.
     *
     * @return A list of all enemies.
     */
    public List<Enemy> getEnemies() {
        return List.copyOf(enemies.values());
    }

    /**
     * Checks if an enemy with the given ID exists.
     *
     * @param enemyId The ID of the enemy to check.
     * @return True if an enemy with the given ID exists, false otherwise.
     */
    public boolean enemyExists(int enemyId) {
        return enemies.containsKey(enemyId);
    }
}
