package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.MapRenderer;
import org.bachelorprojekt.util.TextRenderer;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private GameStateManager gameStateManager;
    private TextRenderer textRenderer;
    private MapRenderer mapRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameStateManager = new GameStateManager();
        textRenderer = new TextRenderer(font, batch);
        mapRenderer = new MapRenderer(font, batch);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameStateManager.isMainMenu()) {
            // Render Main Menu
        } else if (gameStateManager.isMapOpen()) {
            // Render Map
        } else if (gameStateManager.isPaused()) {
            // Render Pause Menu
        } else {
            // Render Game Content
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}