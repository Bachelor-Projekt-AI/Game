package org.bachelorprojekt.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapRenderer {
    private BitmapFont font;
    private SpriteBatch batch;

    public MapRenderer(BitmapFont font, SpriteBatch batch) {
        this.font = font;
        this.batch = batch;
    }

    public void renderMap(String[] map, float startX, float startY) {
        batch.begin();
        for (int i = 0; i < map.length; i++) {
            font.draw(batch, map[i], startX, startY - i * 20);
        }
        batch.end();
    }
}
