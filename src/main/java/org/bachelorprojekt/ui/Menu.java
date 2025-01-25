package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.Scene;
import org.bachelorprojekt.util.TextRenderer;

public class Menu extends Scene {
    private final String[] menuOptions;
    private int selectedOption;
    private final float startY;
    private final TextRenderer textRenderer;

    public Menu(SpriteBatch batch, BitmapFont font, String[] menuOptions) {
        this.menuOptions = menuOptions;
        this.selectedOption = 0;
        this.startY = 280;
        this.textRenderer = new TextRenderer(font, batch);
    }

    @Override
    public void render() {
        textRenderer.drawCenteredText("Lights of Akahzan", 500);
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + menuOptions[i], startY - i * 30);
            } else {
                textRenderer.drawCenteredText(menuOptions[i], startY - i * 30);
            }
        }

        handleInput();
    }

    private void handleInput() {
        // Nach unten navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        }

        // Nach oben navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        }

        // Auswahl bestätigen (falls benötigt, z. B. ENTER-Taste)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Ausgewählte Option: " + menuOptions[selectedOption]);
            // Hier kannst du eine Aktion basierend auf der Auswahl ausführen
        }
    }
}
