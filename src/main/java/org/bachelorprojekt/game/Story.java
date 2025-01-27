package org.bachelorprojekt.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.function.BooleanSupplier;

// Main Story Class
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {
    private boolean isCompleted;
    private String title;
    private String description;
    private List<Chapter> chapters;

    // Getters and Setters
    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Chapter getCurrentChapter(int chapterIndex) {
        return chapters.get(chapterIndex);
    }

    public Quest getCurrentQuest(int chapterIndex, int questIndex) {
        return chapters.get(chapterIndex).getQuests().get(questIndex);
    }


    // Nested Chapter Class
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chapter {
        private String title;
        private String description;
        private List<Quest> quests;
        private List<Map> map;

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

        public List<Map> getMap() {
            return map;
        }

        public BooleanSupplier areAllQuestsCompleted() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'areAllQuestsCompleted'");
        }
    }

    // Nested Quest Class
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quest {
        private boolean isCompleted;
        private String title;
        private String description;
        private List<String> objectives;
        private List<String> rewards;

        public boolean getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
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

        public List<String> getRewards() {
            return rewards;
        }

        public void setRewards(List<String> rewards) {
            this.rewards = rewards;
        }

        public void setCompletedObjectives(List<String> of) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setCompletedObjectives'");
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Map {
        private List<String> mapLines;

        public List<String> getMapLines() {
            return mapLines;
        }

        public void setMapLines(List<String> mapLines) {
            this.mapLines = mapLines;
        }

        // Returns the map as a single string with newlines
        public String getParsedMap() {
            return String.join("\n", mapLines);
        }

        public void setLocations(List<String> of) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setLocations'");
        }

        public BooleanSupplier containsLocation(String string) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'containsLocation'");
        }
    }
}