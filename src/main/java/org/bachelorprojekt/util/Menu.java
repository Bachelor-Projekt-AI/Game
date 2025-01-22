package org.bachelorprojekt.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {
    private SpriteBatch batch;
    private BitmapFont font;
    private String[] menuOptions;

    public Menu(SpriteBatch batch, BitmapFont font, String[] menuOptions) {
        this.batch = batch;
        this.font = font;
        this.menuOptions = menuOptions;
    }

    public void renderMenu(float startX, float startY, int selectedOption) {
        batch.begin();
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                font.draw(batch, "> " + menuOptions[i], startX, startY - i * 30);
            } else {
                font.draw(batch, menuOptions[i], startX, startY - i * 30);
            }
        }
        batch.end();
    }
}
