package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private int id;
    private String name;
    private String description;

    @JsonProperty("npc_ids")
    private List<Integer> npcIds;

    @JsonProperty("connected_location_ids")
    private List<Integer> connectedLocationIds;

    public Location() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.npcIds = List.of();
        this.connectedLocationIds = List.of();
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

    public String getDescription() {
        return description;
    }

    public List<Integer> getNpcIds() {
        return npcIds;
    }

    public List<Integer> getConnectedLocationIds() {
        return connectedLocationIds;
    }

    // =================================================================================================================
    // ==                                          Mapping Methods                                                    ==
    // =================================================================================================================

    private List<NPC> npcs;
    private List<Location> connectedLocations;

    /**
     * Initialisiert die Verknüpfungen für die Location, indem NPCs und benachbarte Orte gesetzt werden.
     *
     * @param npcMap      Ein Mapping aller NPCs anhand ihrer ID.
     * @param locationMap Ein Mapping aller Orte anhand ihrer ID.
     */
    public void initMapping(Map<Integer, Location> locationMap, Map<Integer, NPC> npcMap) {
        // NPCs anhand der IDs setzen
        this.npcs = this.npcIds.stream()
                .map(npcMap::get)  // IDs in NPC-Objekte umwandeln
                .filter(Objects::nonNull)  // Null-Werte herausfiltern
                .collect(Collectors.toList());

        // Verbundene Locations setzen
        this.connectedLocations = this.connectedLocationIds.stream()
                .map(locationMap::get)  // IDs in Location-Objekte umwandeln
                .filter(Objects::nonNull)  // Null-Werte herausfiltern
                .collect(Collectors.toList());
    }

    /**
     * Gibt die NPCs zurück, die sich in dieser Location befinden.
     * @return Eine Liste der NPCs.
     */
    public List<NPC> getNpcs() {
        return npcs != null ? npcs : List.of();
    }

    /**
     * Gibt die verbundenen Orte zurück.
     * @return Eine Liste der verbundenen Locations.
     */
    public List<Location> getConnectedLocations() {
        return connectedLocations != null ? connectedLocations : List.of();
    }
}
