package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestManager {

    private final Map<Integer, Quest> quests;

    /**
     * Initialisiert den QuestManager und führt das Mapping für Quests durch.
     */
    public QuestManager(List<Quest> questList, List<NPC> npcList, List<Item> itemList, List<Location> locationList) {
        this.quests = new HashMap<>();
        Map<Integer, NPC> npcMap = npcList.stream().collect(Collectors.toMap(NPC::getId, npc -> npc));
        Map<Integer, Item> itemMap = itemList.stream().collect(Collectors.toMap(Item::getId, item -> item));
        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));

        for (Quest quest : questList) {
            quest.initMapping(npcMap, itemMap, locationMap);
            quests.put(quest.getId(), quest);
        }
    }

    /**
     * Holt eine Quest anhand ihrer ID.
     */
    public Quest getQuestById(int questId) {
        return quests.get(questId);
    }

    /**
     * Gibt alle Quests zurück.
     */
    public List<Quest> getAllQuests() {
        return List.copyOf(quests.values());
    }

    /**
     * Überprüft, ob eine Quest existiert.
     */
    public boolean questExists(int questId) {
        return quests.containsKey(questId);
    }
}
