package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NPC {
    private int id;
    private String name;

    @JsonProperty("location_id")
    private int locationId;
    private List<String> dialogues;

    public NPC() {
        this.id = 0;
        this.name = "";
        this.locationId = 0;
        this.dialogues = List.of();
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

    public int getLocationId() {
        return locationId;
    }

    public List<String> getDialogues() {
        return dialogues;
    }

    // =================================================================================================================
    // ==                                          Mapping Methods                                                    ==
    // =================================================================================================================

    private Location location;

    /**
     * Initialisiert die Verknüpfung des NPCs mit seiner Location.
     *
     * @param locationMap Ein Mapping aller Locations anhand ihrer ID.
     */
    public void initMapping(Map<Integer, Location> locationMap) {
        this.location = locationMap.get(this.locationId);
    }

    public Location getLocation() {
        return location;
    }
}
