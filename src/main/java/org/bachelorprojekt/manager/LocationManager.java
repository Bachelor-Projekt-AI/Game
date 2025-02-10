package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationManager {

    private final Map<Integer, Location> locations;

    public LocationManager(List<Location> locationList,  List<NPC> npcList) {
        this.locations = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, NPC> npcMap = npcList.stream().collect(Collectors.toMap(NPC::getId, npc -> npc));

        for (Location location : locationList) {
            location.initMapping(locationMap, npcMap);
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
