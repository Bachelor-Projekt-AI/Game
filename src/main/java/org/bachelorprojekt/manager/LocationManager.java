package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the locations in the application, providing methods to retrieve locations by ID,
 * check if a location exists, and get all locations.
 */
public class LocationManager {

    private final Map<Integer, Location> locations;

    /**
     * Constructs a LocationManager instance, initializing the locations from the provided lists.
     * The locations are mapped by their ID, and each location is initialized with corresponding
     * NPC and item data.
     *
     * @param locationList A list of locations to be managed.
     * @param npcList A list of NPCs to be associated with the locations.
     * @param itemList A list of items to be associated with the locations.
     */
    public LocationManager(List<Location> locationList, List<NPC> npcList, List<Item> itemList) {
        this.locations = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, NPC> npcMap = npcList.stream().collect(Collectors.toMap(NPC::getId, npc -> npc));
        Map<Integer, Item> itemMap = itemList.stream().collect(Collectors.toMap(Item::getId, item -> item));

        for (Location location : locationList) {
            location.initMapping(locationMap, npcMap, itemMap);
            locations.put(location.getId(), location);
        }
    }

    /**
     * Retrieves a location by its ID.
     *
     * @param locationId The ID of the location to retrieve.
     * @return The location corresponding to the given ID, or null if not found.
     */
    public Location getLocationById(int locationId) {
        return locations.get(locationId);
    }

    /**
     * Retrieves all locations managed by this LocationManager.
     *
     * @return A list of all locations.
     */
    public List<Location> getAllLocations() {
        return List.copyOf(locations.values());
    }

    /**
     * Checks if a location with the given ID exists.
     *
     * @param locationId The ID of the location to check.
     * @return True if a location with the given ID exists, false otherwise.
     */
    public boolean locationExists(int locationId) {
        return locations.containsKey(locationId);
    }
}
