package org.bachelorprojekt.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Die `Story`-Klasse repräsentiert die gesamte Handlung des Spiels, bestehend aus mehreren Kapiteln.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {
    private boolean isCompleted;
    private String title;
    private String description;
    private List<Chapter> chapters;

    /**
     * Gibt zurück, ob die Story abgeschlossen ist.
     * 
     * @return `true`, wenn die Story abgeschlossen ist, sonst `false`.
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Setzt den Abschlussstatus der Story.
     * 
     * @param isCompleted `true`, wenn die Story abgeschlossen ist, sonst `false`.
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Gibt den Titel der Story zurück.
     * 
     * @return Titel der Story.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel der Story.
     * 
     * @param title Neuer Titel der Story.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gibt die Beschreibung der Story zurück.
     * 
     * @return Beschreibung der Story.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung der Story.
     * 
     * @param description Neue Beschreibung der Story.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt die Liste der Kapitel der Story zurück.
     * 
     * @return Liste der Kapitel.
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * Setzt die Liste der Kapitel für die Story.
     * 
     * @param chapters Neue Kapitel-Liste.
     */
    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    /**
     * Gibt das aktuelle Kapitel basierend auf einem Index zurück.
     * 
     * @param chapterIndex Der Index des Kapitels.
     * @return Das Kapitel-Objekt.
     */
    public Chapter getCurrentChapter(int chapterIndex) {
        return chapters.get(chapterIndex);
    }

    /**
     * Gibt die aktuelle Quest eines bestimmten Kapitels basierend auf den Indizes zurück.
     * 
     * @param chapterIndex Der Index des Kapitels.
     * @param questIndex   Der Index der Quest.
     * @return Das Quest-Objekt.
     */
    public Quest getCurrentQuest(int chapterIndex, int questIndex) {
        return chapters.get(chapterIndex).getQuests().get(questIndex);
    }

    /**
     * Die `Chapter`-Klasse repräsentiert ein Kapitel innerhalb der Story.
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
         * @return Titel des Kapitels.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Setzt den Titel des Kapitels.
         * 
         * @param title Neuer Titel des Kapitels.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gibt die Beschreibung des Kapitels zurück.
         * 
         * @return Beschreibung des Kapitels.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Setzt die Beschreibung des Kapitels.
         * 
         * @param description Neue Beschreibung des Kapitels.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gibt die Liste der Quests in diesem Kapitel zurück.
         * 
         * @return Liste der Quests.
         */
        public List<Quest> getQuests() {
            return quests;
        }

        /**
         * Setzt die Liste der Quests für dieses Kapitel.
         * 
         * @param quests Neue Quest-Liste.
         */
        public void setQuests(List<Quest> quests) {
            this.quests = quests;
        }

        /**
         * Gibt die Kartenobjekte des Kapitels zurück.
         * 
         * @return Liste von Kartenobjekten.
         */
        public List<Map> getMap() {
            return map;
        }

        /**
         * Überprüft, ob alle Quests dieses Kapitels abgeschlossen sind.
         * 
         * @return `true`, wenn alle Quests abgeschlossen sind, sonst `false`.
         */
        public BooleanSupplier areAllQuestsCompleted() {
            throw new UnsupportedOperationException("Unimplemented method 'areAllQuestsCompleted'");
        }
    }

    /**
     * Die `Quest`-Klasse repräsentiert eine Aufgabe innerhalb eines Kapitels.
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
         * @return `true`, wenn die Quest abgeschlossen ist, sonst `false`.
         */
        public boolean getIsCompleted() {
            return isCompleted;
        }

        /**
         * Setzt den Abschlussstatus der Quest.
         * 
         * @param isCompleted `true`, wenn die Quest abgeschlossen ist, sonst `false`.
         */
        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        /**
         * Gibt den Titel der Quest zurück.
         * 
         * @return Titel der Quest.
         */
        public String getTitle() {
            return title;
        }

        /**
         * Setzt den Titel der Quest.
         * 
         * @param title Neuer Titel der Quest.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gibt die Beschreibung der Quest zurück.
         * 
         * @return Beschreibung der Quest.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Setzt die Beschreibung der Quest.
         * 
         * @param description Neue Beschreibung der Quest.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gibt die Liste der Questziele zurück.
         * 
         * @return Liste der Questziele.
         */
        public List<String> getObjectives() {
            return objectives;
        }

        /**
         * Setzt die Liste der Questziele.
         * 
         * @param objectives Neue Liste der Questziele.
         */
        public void setObjectives(List<String> objectives) {
            this.objectives = objectives;
        }

        /**
         * Gibt die Belohnungen für die Quest zurück.
         * 
         * @return Liste der Belohnungen.
         */
        public List<String> getRewards() {
            return rewards;
        }

        /**
         * Setzt die Belohnungen für die Quest.
         * 
         * @param rewards Neue Liste der Belohnungen.
         */
        public void setRewards(List<String> rewards) {
            this.rewards = rewards;
        }
    }

    /**
     * Die `Map`-Klasse repräsentiert eine Karte innerhalb eines Kapitels.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Map {
        private List<String> mapLines;

        /**
         * Gibt die Karte als Liste von Strings zurück.
         * 
         * @return Liste der Kartenzeilen.
         */
        public List<String> getMapLines() {
            return mapLines;
        }

        /**
         * Setzt die Kartenzeilen.
         * 
         * @param mapLines Neue Liste der Kartenzeilen.
         */
        public void setMapLines(List<String> mapLines) {
            this.mapLines = mapLines;
        }

        /**
         * Gibt die Karte als formatierten String zurück.
         * 
         * @return Karte als String mit Zeilenumbrüchen.
         */
        public String getParsedMap() {
            return String.join("\n", mapLines);
        }
    }
}
