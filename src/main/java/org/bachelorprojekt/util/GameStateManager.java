package org.bachelorprojekt.util;

import org.bachelorprojekt.game.Chapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.Story;

import java.util.List;

public class GameStateManager {
    private List<Chapter> chapters; // Story aus JSON geladen
    private Player player;          // Spielerreferenz

    public GameStateManager(List<Chapter> chapters, Player player) {
        this.chapters = chapters;
        this.player = player;
    }

    public Chapter getCurrentChapter() {
        return chapters.get(player.getCurrentChapterIndex());
    }

    public Story.Quest getCurrentQuest() {
        Chapter currentChapter = getCurrentChapter();
        return currentChapter.getQuests().get(player.getCurrentQuestIndex());
    }

    public void completeQuest() {
        Story.Quest currentQuest = getCurrentQuest();
        currentQuest.complete();

        // Prüfen, ob es weitere Quests im Kapitel gibt
        Chapter currentChapter = getCurrentChapter();
        if (currentChapter.getQuests().size() > player.getCurrentQuestIndex() + 1) {
            player.setCurrentQuestIndex(player.getCurrentQuestIndex() + 1);
        } else {
            // Kapitel abschließen und zum nächsten wechseln
            currentChapter.setCompleted(true);
            if (chapters.size() > player.getCurrentChapterIndex() + 1) {
                player.setCurrentChapterIndex(player.getCurrentChapterIndex() + 1);
                player.setCurrentQuestIndex(0); // Nächste Kapitel startet bei der ersten Quest
            }
        }
    }
}
