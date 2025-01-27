package org.bachelorprojekt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class MapRenderer extends ScreenAdapter {
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final Engine engine;

    private final List<String> map;
    private final float startX;
    private final float startY;

    public MapRenderer(Engine engine, List<String> map, float startX, float startY) {
        this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 16);
        this.batch = engine.getBatch();
        this.engine = engine;
        this.map = map;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        for (int i = 0; i < map.size(); i++) {
            font.draw(batch, map.get(i), startX, startY - i * 20);
        }
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            engine.getScreenStack().pop();
        }
    }
}
