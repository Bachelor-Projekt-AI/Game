package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chapter {
    private int id;
    private String title;
    private String description;

    @JsonProperty("quest_ids")
    private List<Integer> questIds;

    @JsonProperty("location_ids")
    private List<Integer> locationIds;

    @JsonProperty("map_id")
    private int mapId;

    public Chapter() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.questIds = List.of();
        this.locationIds = List.of();
        this.mapId = 0;
    }

    // =================================================================================================================
    // ==                                           Getter Methods                                                    ==
    // =================================================================================================================

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getQuestIds() {
        return questIds;
    }

    public List<Integer> getLocationIds() {
        return locationIds;
    }

    public int getMapId() {
        return mapId;
    }

    // =================================================================================================================
    // ==                                          Mapping Methods                                                    ==
    // =================================================================================================================

    private List<Quest> quests;
    private List<Location> locations;
    private Maps map;

    /**
     * Initialisiert die Verknüpfungen für das Kapitel.
     *
     * @param questMap    Ein Mapping aller Quests anhand ihrer ID.
     * @param locationMap Ein Mapping aller Locations anhand ihrer ID.
     * @param mapMap      Ein Mapping aller Karten anhand ihrer ID.
     */
    public void initMapping(Map<Integer, Quest> questMap, Map<Integer, Location> locationMap, Map<Integer, Maps> mapMap) {
        this.quests = this.questIds.stream()
                .map(questMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.locations = this.locationIds.stream()
                .map(locationMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.map = mapMap.get(this.mapId);
    }

    public List<Quest> getQuests() {
        return quests != null ? quests : List.of();
    }

    public List<Location> getLocations() {
        return locations != null ? locations : List.of();
    }

    public Maps getMap() {
        return map;
    }
}
