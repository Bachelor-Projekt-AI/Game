package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class ConfirmSelection extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String message; // Die Nachricht, die angezeigt wird
    private final Runnable onConfirm; // Aktion für "Ja"
    private final Runnable onCancel; // Aktion für "Nein"
    private int selectedOption; // 0 = Yes, 1 = No
    private final float startY;

    public ConfirmSelection(Engine engine, String message, Runnable onConfirm, Runnable onCancel) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.message = message;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.selectedOption = 0; // Standardmäßig "Ja" ausgewählt
        this.startY = 300;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Nachricht anzeigen
        textRenderer.drawCenteredText(message, startY, this.engine.getFont());

        // Optionen anzeigen
        String[] options = {"Yes", "No"};
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + options[i], startY - 50 - (i * 30), this.engine.getFont());
            } else {
                textRenderer.drawCenteredText(options[i], startY - 50 - (i * 30), this.engine.getFont());
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
        // Navigation zwischen "Yes" und "No"
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption == 0) ? 1 : 0; // Toggle zwischen Yes und No
        }

        // Auswahl bestätigen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            engine.popScreen();
            if (selectedOption == 0) {
                System.out.println("Yes");
                onConfirm.run(); // "Yes" ausgewählt
            } else {
                System.out.println("No");
                onCancel.run(); // "No" ausgewählt
            }
             // Schließe das ConfirmSelection-Menü
        }

        // Abbrechen mit ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen();
            onCancel.run();
        }
    }
}
