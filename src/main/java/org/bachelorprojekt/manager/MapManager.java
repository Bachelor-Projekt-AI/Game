package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the maps in the application, providing methods to retrieve maps by ID,
 * check if a map exists, and get all maps.
 */
public class MapManager {

    private final Map<Integer, Maps> maps;

    /**
     * Constructs a MapManager instance, initializing the maps from the provided list.
     * The maps are mapped by their ID, and each map is initialized with corresponding
     * location data.
     *
     * @param mapList A list of maps to be managed.
     * @param locationList A list of locations to be associated with the maps.
     */
    public MapManager(List<Maps> mapList, List<Location> locationList) {
        this.maps = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));

        for (Maps map : mapList) {
            map.initMapping(locationMap);
            maps.put(map.getId(), map);
        }
    }

    /**
     * Retrieves a map by its ID.
     *
     * @param mapId The ID of the map to retrieve.
     * @return The map corresponding to the given ID, or null if not found.
     */
    public Maps getMapById(int mapId) {
        return maps.get(mapId);
    }

    /**
     * Retrieves all maps managed by this MapManager.
     *
     * @return A list of all maps.
     */
    public List<Maps> getAllMaps() {
        return List.copyOf(maps.values());
    }

    /**
     * Checks if a map with the given ID exists.
     *
     * @param mapId The ID of the map to check.
     * @return True if a map with the given ID exists, false otherwise.
     */
    public boolean mapExists(int mapId) {
        return maps.containsKey(mapId);
    }
}
