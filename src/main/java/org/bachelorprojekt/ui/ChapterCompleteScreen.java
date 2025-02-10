package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.chapter.ChapterSystem;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.lwjgl.opengl.GL20;

public class ChapterCompleteScreen implements Screen {
    private final Engine engine;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Chapter currentChapter;
    private int selectedIndex = 0; // 0 = Hauptmenü, 1 = Weiter zu Kapitel Y
    private final Chapter nextChapter;
    private int nextChapterId = -1;
    private final ChapterSystem chapterSystem;

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

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        // Kapitel-Abschluss-Text
        font.draw(batch, "Kapitel " + (currentChapter.getId()) + " abgeschlossen!", centerX - 100, centerY + 50);

        // Optionen
        font.draw(batch, (selectedIndex == 0 ? "> " : "  ") + "Zurück zum Hauptmenü", centerX - 80, centerY);
        if (nextChapter != null) {
            font.draw(batch, (selectedIndex == 1 ? "> " : "  ") + "Weiter zu Kapitel " + nextChapterId, centerX - 80, centerY - 40);
        }

        batch.end();

        handleInput();
    }

    private void handleInput() {
        // Navigation mit Pfeiltasten
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = Math.max(0, selectedIndex - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = Math.min(1, selectedIndex + 1);
        }

        // Auswahl mit ENTER
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Ausgewählte Option: " + selectedIndex);
            if (selectedIndex == 0) {
                engine.quitToMenu(); // Zurück zum Hauptmenü
            } else if (selectedIndex == 1 && nextChapter != null) {
                chapterSystem.startNextChapter(currentChapter.getId() + 1);
            } else {
                // Falls es kein nächstes Kapitel gibt, zurück ins Hauptmenü
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
