package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;

public class LoadingScreen extends ScreenAdapter {
    private final Engine engine;
    private final BitmapFont font;
    private boolean loadingComplete = false;

    public LoadingScreen(Engine engine, Player player, int selectedSlot) {
        this.engine = engine;
        this.font = engine.getFont();

        // Starte das Laden von GameSystemManager in einem neuen Thread
        new Thread(() -> {
            try {
                int chapterIndex = 1; // Start-Kapitel
                GameSystemManager gameSystemManager = new GameSystemManager(engine, player, chapterIndex);

                // Setze den GameSystemManager, wenn er fertig geladen ist
                Gdx.app.postRunnable(() -> {
                    engine.setGameSystemManager(gameSystemManager);
                    loadingComplete = true;

                    // Jetzt erst den ChapterScreen pushen
                    engine.pushScreen(new ChapterScreen(engine, gameSystemManager.getCurrentChapter()));
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void render(float delta) {
        // Bildschirm leeren
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();
        font.draw(engine.getBatch(), "Loading...", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f);
        engine.getBatch().end();
    }
}
