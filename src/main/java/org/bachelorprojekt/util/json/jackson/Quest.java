package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bachelorprojekt.quest.QuestType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quest {
    private int id;
    private String title;
    private String description;
    private List<String> objectives;

    @JsonProperty("npc_id")
    private int npcId;

    @JsonProperty("rewards_item_ids")
    private List<Integer> rewardsItemIds;

    @JsonProperty("required_item_id")
    private int requiredItemId;

    @JsonProperty("required_amount")
    private int requiredAmount;

    @JsonProperty("target_location_id")
    private int targetLocationId;

    @JsonProperty("type")
    private String type;

    public Quest() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.objectives = List.of();
        this.npcId = 0;
        this.rewardsItemIds = List.of();
        this.requiredItemId = 0;
        this.requiredAmount = 0;
        this.targetLocationId = 0;
        this.type = "UNKNOWN";
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

    public List<String> getObjectives() {
        return objectives;
    }

    public List<Integer> getRewardsItemIds() {
        return rewardsItemIds;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getRequiredItemId() {
        return requiredItemId;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public int getTargetLocationId() {
        return targetLocationId;
    }

    // This is replaced with the getter method in the mapping Methods because we want to have the enum
    private String getType() {
        return type;
    }

    // =================================================================================================================
    // ==                                          Mapping Methods                                                    ==
    // =================================================================================================================

    private NPC npc;
    private List<Item> rewardItems;
    private Item requiredItem;
    private Location targetLocation;
    private QuestType questType;

    /**
     * Initialisiert die Verknüpfungen für die Quest, indem NPCs, Items und Locations gesetzt werden.
     *
     * @param npcMap      Ein Mapping aller NPCs anhand ihrer ID.
     * @param itemMap     Ein Mapping aller Items anhand ihrer ID.
     * @param locationMap Ein Mapping aller Orte anhand ihrer ID.
     */
    public void initMapping(java.util.Map<Integer, NPC> npcMap, java.util.Map<Integer, Item> itemMap, java.util.Map<Integer, Location> locationMap) {
        // NPC anhand der ID setzen (falls vorhanden)
        this.npc = npcMap.get(this.npcId);

        // Belohnungs-Items anhand der IDs setzen
        this.rewardItems = this.rewardsItemIds.stream()
                .map(itemMap::get)  // IDs in Item-Objekte übersetzen
                .filter(Objects::nonNull)  // Null-Werte herausfiltern
                .collect(Collectors.toList());

        // Benötigtes Item setzen (falls erforderlich)
        if (this.requiredItemId > 0) {
            this.requiredItem = itemMap.get(this.requiredItemId);
        }

        // Zielort setzen (falls erforderlich)
        if (this.targetLocationId > 0) {
            this.targetLocation = locationMap.get(this.targetLocationId);
        }

        // Quest-Typ aus Enum setzen
        try {
            this.questType = QuestType.valueOf(getType());
        } catch (IllegalArgumentException e) {
            System.err.println("Ungültiger Quest-Typ: " + getType());
            this.questType = QuestType.UNKNOWN;
        }
    }

    /**
     * Gibt den NPC der Quest zurück.
     *
     * @return Der NPC, falls vorhanden, sonst null.
     */
    public NPC getNpc() {
        return npc;
    }

    /**
     * Gibt die Liste der Belohnungsgegenstände zurück.
     *
     * @return Die Liste der Items oder eine leere Liste, falls keine vorhanden sind.
     */
    public List<Item> getRewardItems() {
        return rewardItems != null ? rewardItems : List.of();
    }

    /**
     * Gibt das benötigte Item für die Quest zurück.
     *
     * @return Das benötigte Item oder null, falls keins erforderlich ist.
     */
    public Item getRequiredItem() {
        return requiredItem;
    }

    /**
     * Gibt die Ziel-Location für die Quest zurück.
     *
     * @return Die Location oder null, falls keine erforderlich ist.
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Gibt den Quest-Typ zurück.
     *
     * @return Der `QuestType` der Quest.
     */
    public QuestType getQuestType() {
        return questType;
    }
}
