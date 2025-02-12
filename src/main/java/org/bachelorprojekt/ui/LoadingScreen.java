package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;

/**
 * The LoadingScreen class represents the screen displayed while the game is loading.
 * It shows a loading message until the necessary game systems are initialized.
 * Once loading is complete, the game transitions to the next screen.
 */
public class LoadingScreen extends ScreenAdapter {
    
    private final Engine engine;
    private final BitmapFont font;
    private boolean loadingComplete = false;

    /**
     * Constructs a new LoadingScreen.
     * 
     * @param engine The engine that manages the game logic and rendering.
     * @param player The player whose data will be loaded.
     * @param selectedSlot The slot that was selected for loading the game.
     */
    public LoadingScreen(Engine engine, Player player, int selectedSlot) {
        this.engine = engine;
        this.font = engine.getFont();

        // Start loading the GameSystemManager in a new thread
        new Thread(() -> {
            try {
                int chapterIndex = 1; // Default starting chapter
                GameSystemManager gameSystemManager = new GameSystemManager(engine, player, chapterIndex);

                // Set the GameSystemManager when loading is complete
                Gdx.app.postRunnable(() -> {
                    engine.setGameSystemManager(gameSystemManager);
                    loadingComplete = true;
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Renders the loading screen.
     * The screen will display a "Loading..." message while the game systems are being initialized.
     * Once loading is complete, the game will transition to the next screen.
     * 
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        String loadingText = "Loading...";
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, loadingText);

        engine.getBatch().begin();
        font.draw(engine.getBatch(), loadingText, 960 - layout.width / 2, 540); // Draw centered loading text
        engine.getBatch().end();
    }
}
