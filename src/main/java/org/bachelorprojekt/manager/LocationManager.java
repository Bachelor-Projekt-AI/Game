package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationManager {

    private final Map<Integer, Location> locations;

    public LocationManager(List<Location> locationList,  List<NPC> npcList, List<Item> itemList) {
        this.locations = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, NPC> npcMap = npcList.stream().collect(Collectors.toMap(NPC::getId, npc -> npc));
        Map<Integer, Item> itemMap = itemList.stream().collect(Collectors.toMap(Item::getId, item -> item));

        for (Location location : locationList) {
            location.initMapping(locationMap, npcMap, itemMap);
            locations.put(location.getId(), location);
        }
    }

    public Location getLocationById(int locationId) {
        return locations.get(locationId);
    }

    public List<Location> getAllLocations() {
        return List.copyOf(locations.values());
    }

    public boolean locationExists(int locationId) {
        return locations.containsKey(locationId);
    }
}
