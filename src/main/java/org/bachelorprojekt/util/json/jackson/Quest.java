package org.bachelorprojekt.util.json.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quest {
    private int id;
    private String title;
    private String description;
    private List<String> objectives;

    @JsonProperty("npc_id")
    private int npcId;

    @JsonProperty("rewards_item_ids")
    private List<Integer> rewardsItemIds; // Verknüpft mit Items

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

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public List<Integer> getRewardsItemIds() {
        return rewardsItemIds;
    }

    public void setRewardsItemIds(List<Integer> rewardsItemIds) {
        this.rewardsItemIds = rewardsItemIds;
    }

    public int getNpcId() {
        return npcId;
    }
}
