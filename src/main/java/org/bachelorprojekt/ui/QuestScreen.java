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

import java.util.ArrayList;
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

        // ======================== Spalte 1: Kapitel-Liste ========================
        font.draw(batch, "Kapitel", 48, 1030);
		int chapterLine = 0;
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            String prefix = (i == selectedChapterIndex) ? "> " : "- ";
			List<String> title = splitAtSpace(chapter.getTitle());
			font.draw(batch, prefix + title.removeFirst(), 48, 980 - chapterLine++ * 30);
			for (String line : title) {
				font.draw(batch, "  " + line, 48, 980 - chapterLine++ * 30);
			}
        }

        // ======================== Spalte 2: Quest-Liste ========================
        if (viewState >= 1) {
			float chapterX = 672;
            font.draw(batch, "Quests", chapterX, 1030);
			int questLine = 0;
            for (int i = 0; i < currentQuests.size(); i++) {
                Quest quest = currentQuests.get(i);
                QuestInstance questInstance = questSystem.getQuestInstanceById(quest.getId());
                boolean isActive = questInstance != null && questInstance.isActive();
                boolean isCompleted = questInstance != null && questInstance.isCompleted();

                String status = isCompleted ? " [Abgeschlossen]" : isActive ? " [Aktiv]" : " [Inaktiv]";
                String prefix = (i == selectedQuestIndex) ? "> " : "- ";
				List<String> titleWithStatus = splitAtSpace(quest.getTitle() + status);
				font.draw(batch, prefix + titleWithStatus.removeFirst(), chapterX, 980 - questLine++ * 30);
				for (String line : titleWithStatus) {
					font.draw(batch, "  " + line, chapterX, 980 - questLine++ * 30);
				}
            }
        }

        // ======================== Spalte 3: Quest-Details ========================
        if (viewState == 2 && selectedQuestInstance != null) {
			float detailsX = 1296;
			float offset = 0;
            font.draw(batch, "Quest Details", detailsX, 1030);

			String titleText = "Titel: " + selectedQuestInstance.getQuestData().getTitle();
			List<String> titleLines = splitAtSpace(titleText);
			for (String titleLine : titleLines) {
				font.draw(batch, titleLine, detailsX, 980 - offset);
				offset += 30;
			}

			String descriptionText = "Beschreibung: " + selectedQuestInstance.getQuestData().getDescription();
			List<String> descriptionLines = splitAtSpace(descriptionText);
			for (String descriptionLine : descriptionLines) {
				font.draw(batch, descriptionLine, detailsX, 970 - offset);
				offset += 30;
			}

            // Fortschritt anzeigen
            int progress = selectedQuestInstance.getProgress();
            int required = selectedQuestInstance.getQuestData().getRequiredAmount();
            font.draw(batch, "Fortschritt: " + progress + " / " + required, detailsX, 960 - offset);

            if (!selectedQuestInstance.isActive()) {
                font.draw(batch, "[ENTER] Quest starten", detailsX, 920 - offset);
            } else if (selectedQuestInstance.isCompleted()) {
                font.draw(batch, "Quest abgeschlossen", detailsX, 920 - offset);
            } else {
                font.draw(batch, "[ENTER] Quest abbrechen", detailsX, 920 - offset);
            }
        }

        batch.end();

        handleInput();
    }

	private List<String> splitAtSpace(String input) {
		List<String> list = new ArrayList<String>();
		if (input.length() > 37) {
			int i = 37;
			while (input.charAt(i--) != ' ') ;
			list.add(input.substring(0, i+1));
			list.addAll(splitAtSpace(input.substring(i+2)));
		} else {
			list.add(input);
		}
		return list;
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
