package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.Keybind;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Quest;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.quest.QuestSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * A screen displaying the quest system, where players can view chapters, quests, and their details. 
 * The user can navigate through chapters, view active and completed quests, and interact with them.
 */
public class QuestScreen extends ScreenAdapter {

    private final Engine engine;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GameSystemManager gameSystemManager;
    private final QuestSystem questSystem;

    private int selectedChapterIndex = 0;
    private int selectedQuestIndex = 0;
    private int viewState = 0; // 0 = Chapter View, 1 = Quest View, 2 = Quest Details

    private List<Chapter> chapters;
    private List<Quest> currentQuests;
    private QuestInstance selectedQuestInstance;

    /**
     * Constructs a new QuestScreen instance.
     *
     * @param engine the engine used for rendering and managing game systems
     */
    public QuestScreen(Engine engine) {
        this.engine = engine;
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.gameSystemManager = engine.getGameSystemManager();
        this.questSystem = gameSystemManager.getQuestSystem();

        // Load all chapters, but only display chapters before the current chapter
        int currentChapterId = gameSystemManager.getCurrentChapter().getId();
        this.chapters = gameSystemManager.getChapterManager().getAllChapters().stream()
                .filter(chapter -> chapter.getId() <= currentChapterId)
                .toList();
    }

    /**
     * Renders the screen with the chapter list, quest list, and quest details (depending on the view state).
     *
     * @param delta the time elapsed since the last render (not used in this method)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // ======================== Column 1: Chapter List ========================
        font.draw(batch, "Chapters", 48, 1030);
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

        // ======================== Column 2: Quest List ========================
        if (viewState >= 1) {
            float chapterX = 672;
            font.draw(batch, "Quests", chapterX, 1030);
            int questLine = 0;
            for (int i = 0; i < currentQuests.size(); i++) {
                Quest quest = currentQuests.get(i);
                QuestInstance questInstance = questSystem.getQuestInstanceById(quest.getId());
                boolean isActive = questInstance != null && questInstance.isActive();
                boolean isCompleted = questInstance != null && questInstance.isCompleted();

                String status = isCompleted ? " [Completed]" : isActive ? " [Active]" : " [Inactive]";
                String prefix = (i == selectedQuestIndex) ? "> " : "- ";
                List<String> titleWithStatus = splitAtSpace(quest.getTitle() + status);
                font.draw(batch, prefix + titleWithStatus.removeFirst(), chapterX, 980 - questLine++ * 30);
                for (String line : titleWithStatus) {
                    font.draw(batch, "  " + line, chapterX, 980 - questLine++ * 30);
                }
            }
        }

        // ======================== Column 3: Quest Details ========================
        if (viewState == 2 && selectedQuestInstance != null) {
            float detailsX = 1296;
            float offset = 0;
            font.draw(batch, "Quest Details", detailsX, 1030);

            String titleText = "Title: " + selectedQuestInstance.getQuestData().getTitle();
            List<String> titleLines = splitAtSpace(titleText);
            for (String titleLine : titleLines) {
                font.draw(batch, titleLine, detailsX, 980 - offset);
                offset += 30;
            }

            String descriptionText = "Description: " + selectedQuestInstance.getQuestData().getDescription();
            List<String> descriptionLines = splitAtSpace(descriptionText);
            for (String descriptionLine : descriptionLines) {
                font.draw(batch, descriptionLine, detailsX, 970 - offset);
                offset += 30;
            }

            // Display progress
            int progress = selectedQuestInstance.getProgress();
            int required = selectedQuestInstance.getQuestData().getRequiredAmount();
            font.draw(batch, "Progress: " + progress + " / " + required, detailsX, 960 - offset);

            if (!selectedQuestInstance.isActive()) {
                font.draw(batch, "[ENTER] Start quest", detailsX, 920 - offset);
            } else if (selectedQuestInstance.isCompleted()) {
                font.draw(batch, "Quest completed", detailsX, 920 - offset);
            } else {
                font.draw(batch, "[ENTER] Abort quest", detailsX, 920 - offset);
            }
        }

        batch.end();

        handleInput();
    }

    /**
     * Splits a string into multiple lines based on a maximum character limit per line (36 characters).
     *
     * @param input the string to split
     * @return a list of strings, each representing a line
     */
    private List<String> splitAtSpace(String input) {
        List<String> list = new ArrayList<>();
        if (input.length() > 34) { // 36 chars per line; 2 for selection/indent
            int i = 34;
            while (input.charAt(i--) != ' ') ;
            list.add(input.substring(0, i+1));
            list.addAll(splitAtSpace(input.substring(i+2)));
        } else {
            list.add(input);
        }
        return list;
    }

    /**
     * Handles user input for navigating the quest screen and interacting with quests.
     */
    private void handleInput() {
        new HelpScreen(engine, List.of(
            new Keybind(Input.Keys.BACKSPACE, "Close current menu", () -> {
                if (viewState == 2) {
                    viewState = 1; // Return to quest list
                } else if (viewState == 1) {
                    viewState = 0; // Return to chapter list
                } else {
                    engine.popScreen(); // Exit quest screen
                }
            }),
            new Keybind(Input.Keys.UP, "Move selection up", () -> {
                if (viewState == 0 && selectedChapterIndex > 0) {
                    selectedChapterIndex--;
                } else if (viewState == 1 && selectedQuestIndex > 0) {
                    selectedQuestIndex--;
                }
            }),
            new Keybind(Input.Keys.DOWN, "Move selection down", () -> {
                if (viewState == 0 && selectedChapterIndex < chapters.size() - 1) {
                    selectedChapterIndex++;
                } else if (viewState == 1 && selectedQuestIndex < currentQuests.size() - 1) {
                    selectedQuestIndex++;
                }
            }),
            new Keybind(Input.Keys.ENTER, "Accept selection", () -> {
                if (viewState == 0) {
                    // Chapter selected -> load quests
                    Chapter selectedChapter = chapters.get(selectedChapterIndex);
                    currentQuests = selectedChapter.getQuests();
                    selectedQuestIndex = 0;
                    viewState = 1;
                } else if (viewState == 1) {
                    // Quest selected -> show details
                    Quest selectedQuestData = currentQuests.get(selectedQuestIndex);

                    // Search for active or completed quest instances
                    selectedQuestInstance = questSystem.getQuestInstanceById(selectedQuestData.getId());

                    // If no instance exists, create a temporary one for display
                    if (selectedQuestInstance == null) {
                        selectedQuestInstance = new QuestInstance(selectedQuestData, null);
                    }

                    viewState = 2;
                }
                else if (viewState == 2) {
                    // Start or stop quest
                    if (selectedQuestInstance.isCompleted()) {
                        return; // Quest cannot be stopped once completed
                    }
                    if (!selectedQuestInstance.isActive()) {
                        questSystem.startQuest(selectedQuestInstance.getQuestData().getId());
                    } else {
                        questSystem.stopQuest(selectedQuestInstance.getQuestData().getId());
                    }
                }
            })
        )).render(0);
    }
}
