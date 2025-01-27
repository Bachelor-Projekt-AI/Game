package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Map {
    private int id;
    private String name;
    private String placeholder;

    @JsonProperty("location_ids")
    private List<Integer> locationIds;
    private List<String> layout;

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

    public String getPlaceholder() {
        return placeholder;
    }

    public List<String> getLayout() {
        return layout;
    }

    public List<Integer> getLocationIds() {
        return locationIds;
    }
}
