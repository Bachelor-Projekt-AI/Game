package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Quest;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.quest.QuestSystem;

import java.util.List;

public class QuestScreen extends ScreenAdapter {
    private final Engine engine;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GameSystemManager gameSystemManager;
    private final QuestSystem questSystem;

    private int selectedChapterIndex = 0;
    private int selectedQuestIndex = 0;
    private int viewState = 0; // 0 = Kapitelansicht, 1 = Questansicht, 2 = Questdetails

    private List<Chapter> chapters;
    private List<Quest> currentQuests;
    private QuestInstance selectedQuestInstance;

    public QuestScreen(Engine engine) {
        this.engine = engine;
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.gameSystemManager = engine.getGameSystemManager();
        this.questSystem = gameSystemManager.getQuestSystem();

        // Lade alle Kapitel, aber nur die VOR dem aktuellen Kapitel anzeigen
        int currentChapterId = gameSystemManager.getCurrentChapter().getId();
        this.chapters = gameSystemManager.getChapterManager().getAllChapters().stream()
                .filter(chapter -> chapter.getId() <= currentChapterId)
                .toList();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.begin();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int columnWidth = screenWidth / 3;

        // ======================== Spalte 1: Kapitel-Liste ========================
        font.draw(batch, "Kapitel", 50, screenHeight - 50);
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            String prefix = (i == selectedChapterIndex && viewState == 0) ? "> " : "  ";
            font.draw(batch, prefix + chapter.getTitle(), 50, screenHeight - 100 - (i * 30));
        }

        // ======================== Spalte 2: Quest-Liste ========================
        if (viewState >= 1) {
            font.draw(batch, "Quests", columnWidth + 50, screenHeight - 50);
            for (int i = 0; i < currentQuests.size(); i++) {
                Quest quest = currentQuests.get(i);
                QuestInstance questInstance = questSystem.getQuestInstanceById(quest.getId());
                boolean isActive = questInstance != null && questInstance.isActive();
                boolean isCompleted = questInstance != null && questInstance.isCompleted();

                String status = isCompleted ? " [Abgeschlossen]" : isActive ? " [Aktiv]" : " [Inaktiv]";
                String prefix = (i == selectedQuestIndex && viewState == 1) ? "> " : "  ";
                font.draw(batch, prefix + quest.getTitle() + status, columnWidth + 50, screenHeight - 100 - (i * 30));
            }
        }

        // ======================== Spalte 3: Quest-Details ========================
        if (viewState == 2 && selectedQuestInstance != null) {
            font.draw(batch, "Quest Details", columnWidth * 2 + 50, screenHeight - 50);
            font.draw(batch, "Titel: " + selectedQuestInstance.getQuestData().getTitle(), columnWidth * 2 + 50, screenHeight - 100);
            font.draw(batch, "Beschreibung:", columnWidth * 2 + 50, screenHeight - 130);
            font.draw(batch, selectedQuestInstance.getQuestData().getDescription(), columnWidth * 2 + 50, screenHeight - 160);

            // Fortschritt anzeigen
            int progress = selectedQuestInstance.getProgress();
            int required = selectedQuestInstance.getQuestData().getRequiredAmount();
            font.draw(batch, "Fortschritt: " + progress + " / " + required, columnWidth * 2 + 50, screenHeight - 200);

            if (!selectedQuestInstance.isActive()) {
                font.draw(batch, "[ENTER] Quest starten", columnWidth * 2 + 50, screenHeight - 240);
            } else if (selectedQuestInstance.isCompleted()) {
                font.draw(batch, "Quest abgeschlossen", columnWidth * 2 + 50, screenHeight - 240);
            } else {
                font.draw(batch, "[ENTER] Quest abbrechen", columnWidth * 2 + 50, screenHeight - 240);
            }
        }

        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (viewState == 0 && selectedChapterIndex < chapters.size() - 1) {
                selectedChapterIndex++;
            } else if (viewState == 1 && selectedQuestIndex < currentQuests.size() - 1) {
                selectedQuestIndex++;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (viewState == 0 && selectedChapterIndex > 0) {
                selectedChapterIndex--;
            } else if (viewState == 1 && selectedQuestIndex > 0) {
                selectedQuestIndex--;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (viewState == 0) {
                // Kapitel gewählt -> Quests laden
                Chapter selectedChapter = chapters.get(selectedChapterIndex);
                currentQuests = selectedChapter.getQuests();
                selectedQuestIndex = 0;
                viewState = 1;
            } else if (viewState == 1) {
                // Quest gewählt -> Details anzeigen
                Quest selectedQuestData = currentQuests.get(selectedQuestIndex);

                // Suche in aktiven oder abgeschlossenen Quests
                selectedQuestInstance = questSystem.getQuestInstanceById(selectedQuestData.getId());

                // Falls es keine Instanz gibt, erstelle eine temporäre für die Anzeige
                if (selectedQuestInstance == null) {
                    selectedQuestInstance = new QuestInstance(selectedQuestData, null);
                }

                viewState = 2;
            }
            else if (viewState == 2) {
                // Quest aktivieren oder deaktivieren
                if (selectedQuestInstance.isCompleted()) {
                    return; // Quest kann nicht mehr deaktiviert werden
                }
                if (!selectedQuestInstance.isActive()) {
                    questSystem.startQuest(selectedQuestInstance.getQuestData().getId());
                } else {
                    questSystem.stopQuest(selectedQuestInstance.getQuestData().getId());
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            if (viewState == 2) {
                viewState = 1; // Zurück zu Quest-Liste
            } else if (viewState == 1) {
                viewState = 0; // Zurück zur Kapitel-Liste
            } else {
                engine.popScreen(); // Quest-Screen verlassen
            }
        }
    }
}