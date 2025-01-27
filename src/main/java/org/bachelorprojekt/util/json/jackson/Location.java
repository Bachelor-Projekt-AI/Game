package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private int id;
    private String name;
    private String description;
    private Coordinates coordinates;

    @JsonProperty("npc_ids")
    private List<Integer> npcIds; // Verknüpft mit NPCs
    private List<String> connectedLocations;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Integer> getNpcIds() {
        return npcIds;
    }

    public void setNpcIds(List<Integer> npcIds) {
        this.npcIds = npcIds;
    }

    public List<String> getConnectedLocations() {
        return connectedLocations;
    }

    public void setConnectedLocations(List<String> connectedLocations) {
        this.connectedLocations = connectedLocations;
    }

    // Nested class for coordinates
    public static class Coordinates {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
