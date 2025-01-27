package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chapter {
    private int id;
    private String title;
    private String description;

    @JsonProperty("quest_ids") // Mappt das JSON-Feld "quest_ids" auf dieses Feld
    private List<Integer> questIds;

    @JsonProperty("location_ids") // Mappt das JSON-Feld "location_ids" auf dieses Feld
    private List<Integer> locationIds;

    @JsonProperty("map_id") // Mappt das JSON-Feld "map_id" auf dieses Feld
    private int mapId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getQuestIds() {
        return questIds;
    }

    public void setQuestIds(List<Integer> questIds) {
        this.questIds = questIds;
    }

    public List<Integer> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<Integer> locationIds) {
        this.locationIds = locationIds;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
