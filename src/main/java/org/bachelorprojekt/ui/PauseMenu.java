package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class PauseMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String[] menuOptions = {"Resume", "Save Game", "Quit to Main Menu"};
    private int selectedOption;
    private final float startY;

    public PauseMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedOption = 0; // Standardmäßig die erste Option auswählen
        this.startY = 300; // Y-Position für das Menü
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Titel des Pause-Menüs
        textRenderer.drawCenteredText("Paused", startY, this.engine.getFont());

        // Render die Menüoptionen
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + menuOptions[i], startY - (i + 1) * 30, this.engine.getFont());
            } else {
                textRenderer.drawCenteredText(menuOptions[i], startY - (i + 1) * 30, this.engine.getFont());
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
        // Navigiere nach oben
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        }

        // Navigiere nach unten
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        }

        // Bestätige die aktuelle Auswahl
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedOption) {
                case 0: // Resume
                    engine.popScreen(); // Kehre zurück zum Kapitel
                    break;
                case 1: // Save Game
                    saveGame();
                    break;
                case 2: // Quit to Main Menu
                    engine.quitToMenu();
                    break;
            }
        }

        // Menü schließen, wenn erneut ESC gedrückt wird
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen(); // Kehre zurück zum Kapitel
        }
    }

    private void saveGame() {
        // Spielstand speichern
        //engine.getGameStateManager().saveGame();
        System.out.println("Game saved.");
    }
}
