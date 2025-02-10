package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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

		String loadingText = "Loading...";
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, loadingText);

        engine.getBatch().begin();
        font.draw(engine.getBatch(), loadingText, 960 - layout.width, 540);
        engine.getBatch().end();
    }
}
