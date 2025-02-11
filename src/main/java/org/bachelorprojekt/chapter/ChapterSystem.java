package org.bachelorprojekt.chapter;

import com.badlogic.gdx.Gdx;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.ui.ChapterCompleteScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.json.jackson.Chapter;

import java.util.*;
/**
 * Das ChapterSystem verwaltet den Fortschritt der Kapitel im Spiel.
 * Es lädt Kapitel, überprüft den Abschluss von Quests und startet neue Kapitel.
 */
public class ChapterSystem {
    private final Engine engine;
    private Chapter currentChapter;
    private final GameSystemManager gm;
    private final List<Integer> completedChapters = new ArrayList<>();
    private final Map<Integer, Chapter> chapterRepository = new HashMap<>();

    /**
     * Konstruktor des ChapterSystems.
     * 
     * @param engine        Die Spielengine
     * @param chapterIndex  Der Index des zu startenden Kapitels
     * @param gm            Das GameSystemManager-Objekt
     */
    public ChapterSystem(Engine engine, int chapterIndex, GameSystemManager gm) {
        this.engine = engine;
        this.gm = gm;
        loadAllChapters();
        Gdx.app.postRunnable(() -> startNextChapter(chapterIndex));
    }

    /**
     * Lädt alle Kapitel aus dem Kapitel-Manager und speichert sie im Repository.
     */
    private void loadAllChapters() {
        List<Chapter> allChapters = gm.getChapterManager().getAllChapters();
        for (Chapter chapter : allChapters) {
            chapterRepository.put(chapter.getId(), chapter);
        }
    }

    /**
     * Aktualisiert den Zustand des aktuellen Kapitels.
     * Überprüft, ob alle Quests abgeschlossen sind und triggert den Abschluss des Kapitels.
     */
    public void update() {
        if (currentChapter == null) return; // Sicherheitscheck

        List<QuestInstance> questInstances = engine.getGameSystemManager().getQuestSystem().getCompletedQuests();
        boolean allQuestsCompleted = currentChapter.getQuestIds().stream()
                .allMatch(questId -> questInstances.stream()
                        .anyMatch(questInstance -> questInstance.getQuestData().getId() == questId));

        if (allQuestsCompleted && completedChapters.stream().noneMatch(id -> id == currentChapter.getId())) {
            triggerChapterCompletion();
        }
    }

    /**
     * Triggert den Abschluss des aktuellen Kapitels und zeigt den Abschlussbildschirm an.
     */
    private void triggerChapterCompletion() {
        System.out.println("Kapitel " + currentChapter.getId() + " abgeschlossen!");
        completedChapters.add(currentChapter.getId());
        engine.pushScreen(new ChapterCompleteScreen(engine));
    }

    /**
     * Startet das nächste Kapitel basierend auf dem Kapitelindex.
     * Falls das Kapitel nicht existiert, wird das Hauptmenü geladen.
     * 
     * @param chapterIndex Der Index des zu startenden Kapitels
     */
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

    /**
     * Gibt das aktuell laufende Kapitel zurück.
     * 
     * @return Das aktuelle Kapitel oder null, wenn kein Kapitel aktiv ist.
     */
    public Chapter getCurrentChapter() {
        return currentChapter;
    }
}
