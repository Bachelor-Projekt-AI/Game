package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderableElement implements IRenderable {
    private final float xPercent;
    private final float yPercent;
    private final String text;

    public RenderableElement(float xPercent, float yPercent, String text) {
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.text = text;
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        float x = Gdx.graphics.getWidth() * xPercent / 100;
        float y = Gdx.graphics.getHeight() * (1 - yPercent / 100);
        font.draw(batch, text, x, y);
    }
}
