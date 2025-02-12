package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.NPC;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the quests in the application, providing methods to retrieve quests by ID,
 * check if a quest exists, and get all quests.
 */
public class QuestManager {

    private final Map<Integer, Quest> quests;

    /**
     * Constructs a QuestManager instance, initializing the quests from the provided lists.
     * The quests are mapped by their ID, and each quest is initialized with corresponding
     * NPC, item, and location data.
     *
     * @param questList A list of quests to be managed.
     * @param npcList A list of NPCs to be associated with the quests.
     * @param itemList A list of items to be associated with the quests.
     * @param locationList A list of locations to be associated with the quests.
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
     * Retrieves a quest by its ID.
     *
     * @param questId The ID of the quest to retrieve.
     * @return The quest corresponding to the given ID, or null if not found.
     */
    public Quest getQuestById(int questId) {
        return quests.get(questId);
    }

    /**
     * Retrieves all quests managed by this QuestManager.
     *
     * @return A list of all quests.
     */
    public List<Quest> getAllQuests() {
        return List.copyOf(quests.values());
    }

    /**
     * Checks if a quest with the given ID exists.
     *
     * @param questId The ID of the quest to check.
     * @return True if a quest with the given ID exists, false otherwise.
     */
    public boolean questExists(int questId) {
        return quests.containsKey(questId);
    }
}
