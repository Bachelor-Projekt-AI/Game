package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class ContextMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String[] options = {
            "Suche nach Gegenständen",
            "Spreche mit Charakteren",
            "Untersuche ein bestimmtes Objekt"
    };
    private int selectedOption;
    private final float startY;

    public ContextMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedOption = 0; // Standardmäßig die erste Option ausgewählt
        this.startY = 300;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Zeichne den Menü-Titel
        textRenderer.drawCenteredText("Was möchtest du tun?", startY);

        // Zeichne die Optionen
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + options[i], startY - 50 - (i * 30));
            } else {
                textRenderer.drawCenteredText(options[i], startY - 50 - (i * 30));
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
        // Navigation zwischen Optionen
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % options.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + options.length) % options.length;
        }

        // Auswahl bestätigen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            executeOption(selectedOption);
        }

        // Abbrechen des Menüs
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen(); // Gehe zurück zum vorherigen Screen
        }
    }

    private void executeOption(int option) {
        switch (option) {
            case 0 -> System.out.println("Du suchst nach Gegenständen...");
            case 1 -> System.out.println("Du sprichst mit Charakteren...");
            case 2 -> System.out.println("Du untersuchst ein bestimmtes Objekt...");
            default -> throw new IllegalStateException("Unerwartete Option: " + option);
        }

        // Nach der Aktion kehrt das Spiel z. B. zurück zum vorherigen Screen
        engine.popScreen();
    }
}
