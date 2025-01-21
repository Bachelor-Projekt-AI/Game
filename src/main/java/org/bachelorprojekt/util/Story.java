package org.bachelorprojekt.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

// Main Story Class
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {
    private String title;
    private String description;
    private List<Chapter> chapters;

    // Getters and Setters
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    // Nested Chapter Class
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chapter {
        private String title;
        private String description;
        private List<Quest> quests;

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

        public List<Quest> getQuests() {
            return quests;
        }

        public void setQuests(List<Quest> quests) {
            this.quests = quests;
        }
    }

    // Nested Quest Class
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quest {
        private String title;
        private String description;
        private List<String> objectives;
        private List<String> rewards;

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

        public List<String> getRewards() {
            return rewards;
        }

        public void setRewards(List<String> rewards) {
            this.rewards = rewards;
        }
    }

    // Main Method to Parse JSON
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // print current dir
            System.out.println("Current dir: " + System.getProperty("user.dir"));
            Story story = mapper.readValue(new File("src/main/resources/story/chapters.json"), Story.class);
            System.out.println("Title: " + story.getTitle());
            System.out.println("Description: " + story.getDescription());
            for (Chapter chapter : story.getChapters()) {
                System.out.println("Chapter: " + chapter.getTitle());
                for (Quest quest : chapter.getQuests()) {
                    System.out.println("  Quest: " + quest.getTitle());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}