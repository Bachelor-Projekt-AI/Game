package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.chapter.ChapterSystem;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.lwjgl.opengl.GL20;

/**
 * Displays the screen that appears when a chapter is completed. It shows options to either go back to the main menu
 * or continue to the next chapter if available.
 */
public class ChapterCompleteScreen implements Screen {
    private final Engine engine;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Chapter currentChapter;
    private int selectedIndex = 0; // 0 = Main Menu, 1 = Continue to next Chapter
    private final Chapter nextChapter;
    private int nextChapterId = -1;
    private final ChapterSystem chapterSystem;

    /**
     * Constructs a ChapterCompleteScreen to display after completing a chapter.
     *
     * @param engine The game engine instance for accessing resources like batch and font.
     */
    public ChapterCompleteScreen(Engine engine) {
        this.engine = engine;
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.chapterSystem = engine.getGameSystemManager().getChapterSystem();
        this.currentChapter = engine.getGameSystemManager().getCurrentChapter();

        nextChapter = engine.getGameSystemManager().getChapterManager().getChapterById(currentChapter.getId() + 1);
        if (nextChapter != null) {
            nextChapterId = nextChapter.getId();
        }
    }

    /**
     * Renders the chapter completion screen, displaying the chapter completion message
     * and the available options to the user.
     *
     * @param delta Time elapsed since the last frame, used for animations.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Chapter completion text
        String chapterFinishText = "Kapitel " + (currentChapter.getId()) + " abgeschlossen!";
        font.draw(batch, chapterFinishText, getCenteredXStart(chapterFinishText), 590);

        // Options
        String toMenu = (selectedIndex == 0 ? "> " : "  ") + "Zurück zum Hauptmenü";
        font.draw(batch, toMenu, getCenteredXStart(toMenu), 540);
        if (nextChapter != null) {
            String toNextChapter = (selectedIndex == 1 ? "> " : "  ") + "Weiter zu Kapitel " + nextChapterId;
            font.draw(batch, toNextChapter, getCenteredXStart(toNextChapter), 500);
        }

        batch.end();

        handleInput();
    }

    /**
     * Centers the given text horizontally on the screen.
     *
     * @param text The text to center.
     * @return The starting x-coordinate for the centered text.
     */
    private int getCenteredXStart(String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return 960 - (int) layout.width / 2;
    }

    /**
     * Handles user input for navigation and selection.
     * Arrow keys are used to navigate between options, and ENTER is used to select.
     */
    private void handleInput() {
        // Navigation with arrow keys
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = Math.max(0, selectedIndex - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = Math.min(1, selectedIndex + 1);
        }

        // Selection with ENTER
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Ausgewählte Option: " + selectedIndex);
            if (selectedIndex == 0) {
                engine.quitToMenu(); // Back to the main menu
            } else if (selectedIndex == 1 && nextChapter != null) {
                chapterSystem.startNextChapter(currentChapter.getId() + 1);
            } else {
                // If there's no next chapter, go back to the main menu
                engine.quitToMenu();
            }
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
