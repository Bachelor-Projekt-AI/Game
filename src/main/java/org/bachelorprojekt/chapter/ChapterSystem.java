package org.bachelorprojekt.chapter;

import com.badlogic.gdx.Gdx;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.ui.ChapterCompleteScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.json.jackson.Chapter;

import java.util.*;

public class ChapterSystem {
    private final Engine engine;
    private Chapter currentChapter;
    private final GameSystemManager gm;
    private final List<Integer> completedChapters = new ArrayList<>();
    private final Map<Integer, Chapter> chapterRepository = new HashMap<>();

    public ChapterSystem(Engine engine, int chapterIndex, GameSystemManager gm) {
        this.engine = engine;
        this.gm = gm;
        loadAllChapters();

        Gdx.app.postRunnable(() -> startNextChapter(chapterIndex));
        //startNextChapter(chapterIndex);
    }

    private void loadAllChapters() {
        List<Chapter> allChapters = gm.getChapterManager().getAllChapters();
        for (Chapter chapter : allChapters) {
            chapterRepository.put(chapter.getId(), chapter);
        }
    }

    public void update() {
        if (currentChapter == null) return; // Sicherheitscheck

        // Prüfen, ob alle Quests abgeschlossen sind
        List<QuestInstance> questInstances = engine.getGameSystemManager().getQuestSystem().getCompletedQuests();
        boolean allQuestsCompleted = currentChapter.getQuestIds().stream()
                .allMatch(questId -> questInstances.stream()
                        .anyMatch(questInstance -> questInstance.getQuestData().getId() == questId));

        if (allQuestsCompleted && completedChapters.stream().noneMatch(id -> id == currentChapter.getId())) {
            triggerChapterCompletion();
        }
    }

    private void triggerChapterCompletion() {
        System.out.println("Kapitel " + currentChapter.getId() + " abgeschlossen!");
        completedChapters.add(currentChapter.getId());
        engine.pushScreen(new ChapterCompleteScreen(engine));
    }

    public void startNextChapter(int chapterIndex) {
        if (!chapterRepository.containsKey(chapterIndex)) {
            System.out.println("Kein weiteres Kapitel vorhanden. Zurück ins Hauptmenü.");
            engine.quitToMenu();
            return;
        }

        Chapter nextChapter = chapterRepository.get(chapterIndex);

        engine.pushScreen(new ChapterScreen(engine, nextChapter, gm));
        currentChapter = nextChapter;

        System.out.println("Kapitel " + chapterIndex + " gestartet!");
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }
}
