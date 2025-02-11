package org.bachelorprojekt.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Repräsentiert eine Hauptgeschichte im Spiel, die mehrere Kapitel und Quests enthalten kann.
 * Diese Klasse enthält Informationen zur Geschichte, einschließlich des Titels, der Beschreibung und der Kapitel.
 * 
 * @author [Dein Name]
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {
    private boolean isCompleted;
    private String title;
    private String description;
    private List<Chapter> chapters;

    /**
     * Gibt zurück, ob die Geschichte abgeschlossen ist.
     * 
     * @return true, wenn die Geschichte abgeschlossen ist, andernfalls false.
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Setzt den Abschlussstatus der Geschichte.
     * 
     * @param isCompleted true, wenn die Geschichte abgeschlossen ist, andernfalls false.
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Gibt den Titel der Geschichte zurück.
     * 
     * @return Der Titel der Geschichte.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel der Geschichte.
     * 
     * @param title Der Titel der Geschichte.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gibt die Beschreibung der Geschichte zurück.
     * 
     * @return Die Beschreibung der Geschichte.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung der Geschichte.
     * 
     * @param description Die Beschreibung der Geschichte.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt eine Liste von Kapiteln in der Geschichte zurück.
     * 
     * @return Eine Liste von Kapiteln.
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * Setzt die Kapitel der Geschichte.
     * 
     * @param chapters Eine Liste von Kapiteln.
     */
    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    /**
     * Gibt das Kapitel an einem bestimmten Index zurück.
     * 
     * @param chapterIndex Der Index des Kapitels.
     * @return Das Kapitel an diesem Index.
     */
    public Chapter getCurrentChapter(int chapterIndex) {
        return chapters.get(chapterIndex);
    }

    /**
     * Gibt die Quest eines Kapitels an einem bestimmten Index zurück.
     * 
     * @param chapterIndex Der Index des Kapitels.
     * @param questIndex Der Index der Quest im Kapitel.
     * @return Die Quest an diesem Index.
     */
    public Quest getCurrentQuest(int chapterIndex, int questIndex) {
        return chapters.get(chapterIndex).getQuests().get(questIndex);
    }

    /**
     * Repräsentiert ein Kapitel innerhalb einer Geschichte, das mehrere Quests und Karten enthalten kann.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chapter {
        private String title;
        private String description;
        private List<Quest> quests;
        private List<Map> map;

        /**
         * Gibt den Titel des Kapitels zurück.
         * 
         * @return Der Titel des Kapitels.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Setzt den Titel des Kapitels.
         * 
         * @param title Der Titel des Kapitels.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gibt die Beschreibung des Kapitels zurück.
         * 
         * @return Die Beschreibung des Kapitels.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Setzt die Beschreibung des Kapitels.
         * 
         * @param description Die Beschreibung des Kapitels.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gibt die Liste von Quests im Kapitel zurück.
         * 
         * @return Eine Liste von Quests.
         */
        public List<Quest> getQuests() {
            return quests;
        }

        /**
         * Setzt die Quests des Kapitels.
         * 
         * @param quests Eine Liste von Quests.
         */
        public void setQuests(List<Quest> quests) {
            this.quests = quests;
        }

        /**
         * Gibt die Karte des Kapitels zurück.
         * 
         * @return Eine Liste von Kartenobjekten.
         */
        public List<Map> getMap() {
            return map;
        }

        /**
         * Überprüft, ob alle Quests im Kapitel abgeschlossen sind.
         * 
         * @return Ein BooleanSupplier, der prüft, ob alle Quests abgeschlossen sind.
         */
        public BooleanSupplier areAllQuestsCompleted() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'areAllQuestsCompleted'");
        }
    }

    /**
     * Repräsentiert eine Quest innerhalb eines Kapitels.
     * Eine Quest enthält Ziele und Belohnungen und kann als abgeschlossen markiert werden.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quest {
        private boolean isCompleted;
        private String title;
        private String description;
        private List<String> objectives;
        private List<String> rewards;

        /**
         * Gibt zurück, ob die Quest abgeschlossen ist.
         * 
         * @return true, wenn die Quest abgeschlossen ist, andernfalls false.
         */
        public boolean getIsCompleted() {
            return isCompleted;
        }

        /**
         * Setzt den Abschlussstatus der Quest.
         * 
         * @param isCompleted true, wenn die Quest abgeschlossen ist, andernfalls false.
         */
        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        /**
         * Gibt den Titel der Quest zurück.
         * 
         * @return Der Titel der Quest.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Setzt den Titel der Quest.
         * 
         * @param title Der Titel der Quest.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gibt die Beschreibung der Quest zurück.
         * 
         * @return Die Beschreibung der Quest.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Setzt die Beschreibung der Quest.
         * 
         * @param description Die Beschreibung der Quest.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gibt die Ziele der Quest zurück.
         * 
         * @return Eine Liste von Zielen.
         */
        public List<String> getObjectives() {
            return objectives;
        }

        /**
         * Setzt die Ziele der Quest.
         * 
         * @param objectives Eine Liste von Zielen.
         */
        public void setObjectives(List<String> objectives) {
            this.objectives = objectives;
        }

        /**
         * Gibt die Belohnungen der Quest zurück.
         * 
         * @return Eine Liste von Belohnungen.
         */
        public List<String> getRewards() {
            return rewards;
        }

        /**
         * Setzt die Belohnungen der Quest.
         * 
         * @param rewards Eine Liste von Belohnungen.
         */
        public void setRewards(List<String> rewards) {
            this.rewards = rewards;
        }

        /**
         * Setzt die abgeschlossenen Ziele der Quest.
         * 
         * @param of Eine Liste von abgeschlossenen Zielen.
         */
        public void setCompletedObjectives(List<String> of) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setCompletedObjectives'");
        }
    }

    /**
     * Repräsentiert eine Karte innerhalb eines Kapitels.
     * Eine Karte enthält mehrere Zeilen, die das Spielumfeld darstellen.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Map {
        private List<String> mapLines;

        /**
         * Gibt die Zeilen der Karte zurück.
         * 
         * @return Eine Liste von Zeilen, die die Karte darstellen.
         */
        public List<String> getMapLines() {
            return mapLines;
        }

        /**
         * Setzt die Zeilen der Karte.
         * 
         * @param mapLines Eine Liste von Zeilen, die die Karte darstellen.
         */
        public void setMapLines(List<String> mapLines) {
            this.mapLines = mapLines;
        }

        /**
         * Gibt die Karte als einzelnen String zurück, der die Zeilen mit Zeilenumbrüchen verbindet.
         * 
         * @return Die Karte als String.
         */
        public String getParsedMap() {
            return String.join("\n", mapLines);
        }

        /**
         * Setzt die Standorte auf der Karte.
         * 
         * @param of Eine Liste von Standorten.
         */
        public void setLocations(List<String> of) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setLocations'");
        }

        /**
         * Überprüft, ob ein bestimmter Standort auf der Karte vorhanden ist.
         * 
         * @param string Der zu überprüfende Standort.
         * @return Ein BooleanSupplier, der prüft, ob der Standort vorhanden ist.
         */
        public BooleanSupplier containsLocation(String string) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'containsLocation'");
        }
    }
}
