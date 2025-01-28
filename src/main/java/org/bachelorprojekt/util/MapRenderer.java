package org.bachelorprojekt.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapRenderer {
    private final BitmapFont font;
    private final SpriteBatch batch;

    public MapRenderer(Engine engine) {
        this.font = engine.getFont();
        this.batch = engine.getBatch();
    }

    public void renderMap(String[] map, float startX, float startY) {
        batch.begin();
        for (int i = 0; i < map.length; i++) {
            font.draw(batch, map[i], startX, startY - i * 20);
        }
        batch.end();
    }
}
