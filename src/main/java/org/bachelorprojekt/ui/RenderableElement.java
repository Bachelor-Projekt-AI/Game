package org.bachelorprojekt.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderableElement implements IRenderable {
    private final int zoneX;
    private final int zoneY;
    private final String text;

    public RenderableElement(int zoneX, int zoneY, String text) {
        this.zoneX = zoneX;
        this.zoneY = zoneY;
        this.text = text;
    }

    public int getZoneX() {
        return zoneX;
    }

    public int getZoneY() {
        return zoneY;
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        // Position basierend auf Zonen berechnen
        float x = zoneX * 100; // Beispiel: Jede Zone ist 100 Pixel breit
        float y = 800 - (zoneY * 100); // Beispiel: Jede Zone ist 100 Pixel hoch (invertierte Y-Achse)
        font.draw(batch, text, x, y);
    }
}
