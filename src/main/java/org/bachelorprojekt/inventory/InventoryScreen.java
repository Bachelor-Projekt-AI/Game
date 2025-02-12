package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the inventory screen of the game.
 */
public class InventoryScreen implements Screen {
    private final MyGdxGame game;
    private final SpriteBatch batch;
    private final Texture inventoryTexture;

    /**
     * Constructs an InventoryScreen with the given game instance.
     * 
     * @param game the game instance
     */
    public InventoryScreen(MyGdxGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.inventoryTexture = new Texture(Gdx.files.internal("inventory.png"));
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(inventoryTexture, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Handle resizing if necessary.
    }

    @Override
    public void pause() {
        // Called when the game is paused.
    }

    @Override
    public void resume() {
        // Called when the game is resumed.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen.
    }

    @Override
    public void dispose() {
        // Dispose of assets when no longer needed.
        batch.dispose();
        inventoryTexture.dispose();
    }
}
