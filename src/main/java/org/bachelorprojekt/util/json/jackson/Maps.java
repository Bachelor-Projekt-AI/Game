package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Maps {
    private int id;
    private String name;
    private String placeholder;

    @JsonProperty("location_ids")
    private List<Integer> locationIds;
    private List<String> layout;

    public Maps() {
        this.id = 0;
        this.name = "";
        this.placeholder = "";
        this.locationIds = List.of();
        this.layout = List.of();
    }

    // =================================================================================================================
    // ==                                           Getter Methods                                                    ==
    // =================================================================================================================

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    // =================================================================================================================
    // ==                                          Mapping Methods                                                    ==
    // =================================================================================================================

    private List<Location> locations;

    /**
     * Initialisiert die Verknüpfung der Map mit ihren Locations.
     *
     * @param locationMap Ein Mapping aller Locations anhand ihrer ID.
     */
    public void initMapping(java.util.Map<Integer, Location> locationMap) {
        this.locations = this.locationIds.stream()
                .map(locationMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Location> getLocations() {
        return locations != null ? locations : List.of();
    }
}
