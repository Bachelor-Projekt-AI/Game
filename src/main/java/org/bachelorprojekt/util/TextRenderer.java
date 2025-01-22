package org.bachelorprojekt.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderer {
    private BitmapFont font;
    private SpriteBatch batch;

    public TextRenderer(BitmapFont font, SpriteBatch batch) {
        this.font = font;
        this.batch = batch;
    }

    public void drawCenteredText(String text, float centerX, float centerY) {
        String[] lines = text.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            float textWidth = font.getRegion().getRegionWidth() / (float) font.getRegion().getRegionHeight() * lines[i].length();
            font.draw(batch, lines[i], centerX - textWidth / 2f, centerY - (i * 30));
        }
    }
}
