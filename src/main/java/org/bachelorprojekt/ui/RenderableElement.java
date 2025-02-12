package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a renderable element on the screen with a specified position (in percentages) 
 * and text that is drawn using a given font.
 */
public class RenderableElement implements IRenderable {

    private final float xPercent;
    private final float yPercent;
    private final String text;

    /**
     * Constructs a new RenderableElement with the specified position and text.
     *
     * @param xPercent the x-position as a percentage of the screen width (0-100)
     * @param yPercent the y-position as a percentage of the screen height (0-100)
     * @param text the text to be rendered
     */
    public RenderableElement(float xPercent, float yPercent, String text) {
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.text = text;
    }

    /**
     * Renders the text element at the specified position on the screen using the provided font.
     * The position is calculated based on the screen width and height, where xPercent 
     * and yPercent define the position relative to the screen size.
     *
     * @param batch the SpriteBatch used for drawing the text
     * @param font the BitmapFont used to render the text
     */
    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        // Calculate the x and y positions in screen pixels based on the percentage
        float x = 1920 * xPercent / 100;
        float y = 1080 * (1 - yPercent / 100);
        
        // Draw the text at the calculated position
        font.draw(batch, text, x, y);
    }
}
