package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NpcManager {

    private final Map<Integer, NPC> npcs;

    /**
     * Erstellt einen NPC-Manager und speichert alle NPCs in einer HashMap zur schnellen Suche.
     *
     * @param npcList Die Liste der geladenen NPCs.
     */
    public NpcManager(List<NPC> npcList, List<Location> locationList) {
        this.npcs = new HashMap<>();

        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));

        for (NPC npc : npcList) {
            npc.initMapping(locationMap);
            npcs.put(npc.getId(), npc);
        }
    }

    /**
     * Holt einen NPC anhand seiner ID.
     *
     * @param npcId Die ID des gesuchten NPCs.
     * @return Der NPC oder null, falls keiner gefunden wurde.
     */
    public NPC getNpcById(int npcId) {
        return npcs.get(npcId);
    }

    /**
     * Gibt eine Liste aller NPCs zurück.
     *
     * @return Liste aller NPCs.
     */
    public List<NPC> getNpcs() {
        return List.copyOf(npcs.values());
    }

    /**
     * Prüft, ob ein NPC mit einer bestimmten ID existiert.
     *
     * @param npcId Die ID des gesuchten NPCs.
     * @return true, wenn der NPC existiert, sonst false.
     */
    public boolean npcExists(int npcId) {
        return npcs.containsKey(npcId);
    }
}
