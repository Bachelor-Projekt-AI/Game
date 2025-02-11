package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Enemy;
import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnemyManager {
    private final Map<Integer, Enemy> enemies;

    public EnemyManager(List<Enemy> enemyList, List<Location> locationList, List<Item> itemList) {
        this.enemies = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, Item> itemMap = itemList.stream().collect(Collectors.toMap(Item::getId, npc -> npc));

        for (Enemy enemy : enemyList) {
            enemy.initMapping(locationMap, itemMap);
            enemies.put(enemy.getId(), enemy);
        }
    }

    public Enemy getEnemyById(int enemyId) {
        return enemies.get(enemyId);
    }

    public List<Enemy> getEnemies() {
        return List.copyOf(enemies.values());
    }

    public boolean enemyExists(int enemyId) {
        return enemies.containsKey(enemyId);
    }
}
