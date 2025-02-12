package org.bachelorprojekt.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The IRenderable interface is used for objects that can be rendered on the screen.
 * Any class implementing this interface must provide its own implementation of the render method.
 */
public interface IRenderable {

    /**
     * Renders the object to the screen.
     * 
     * @param batch The SpriteBatch used to draw the object.
     * @param font The BitmapFont used to render text, if applicable.
     */
    void render(SpriteBatch batch, BitmapFont font);
}
