package org.bachelorprojekt.util;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapRenderer extends ScreenAdapter {
    private final BitmapFont font;
    private final SpriteBatch batch;

    private final String[] map;
    private final float startX;
    private final float startY;

    public MapRenderer(Engine engine, String[] map, float startX, float startY) {
        this.font = engine.getFont();
        this.batch = engine.getBatch();
        this.map = map;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        for (int i = 0; i < map.length; i++) {
            font.draw(batch, map[i], startX, startY - i * 20);
        }
        batch.end();
    }

    protected void handleInput() {

    }
}
