package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapManager {

    private final Map<Integer, Maps> maps;

    public MapManager(List<Maps> mapList, List<Location> locationList) {
        this.maps = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));

        for (Maps map : mapList) {
            map.initMapping(locationMap);
            maps.put(map.getId(), map);
        }
    }

    public Maps getMapById(int mapId) {
        return maps.get(mapId);
    }

    public List<Maps> getAllMaps() {
        return List.copyOf(maps.values());
    }

    public boolean mapExists(int mapId) {
        return maps.containsKey(mapId);
    }
}
