package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class ConfirmSelection extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final int selectedSlot;
    private final boolean saveExists;
    private int selectedOption; // 0 = Yes, 1 = No
    private final float startY;

    public ConfirmSelection(Engine engine, int selectedSlot, boolean saveExists) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedSlot = selectedSlot;
        this.saveExists = saveExists;
        this.startY = 300;
        this.selectedOption = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Zeige die Hauptfrage
        String action = saveExists ? "Load existing game?" : "Create new game?";
        textRenderer.drawCenteredText(action, startY);

        // Render nur die Auswahl mit Markierung
        if (selectedOption == 0) {
            textRenderer.drawCenteredText("> Yes", startY - 50); // Markierter Text für "Yes"
            textRenderer.drawCenteredText("No", startY - 80);    // Unmarkierter Text für "No"
        } else {
            textRenderer.drawCenteredText("Yes", startY - 50);   // Unmarkierter Text für "Yes"
            textRenderer.drawCenteredText("> No", startY - 80);  // Markierter Text für "No"
        }

        engine.getBatch().end();

        handleInput();
    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption == 0) ? 1 : 0; // Wechsel zwischen Yes und No
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption == 0) { // Bestätige Auswahl
                if (false) {
//                    // Lade Spielstand und gehe zum Kapitel
//                    engine.getGameStateManager().loadGameFromSlot(selectedSlot);
//                    engine.pushScreen(new ChapterOne(engine));
                } else {
                    // Gehe zum EnterNameMenu für ein neues Spiel
                    engine.pushScreen(new EnterNameMenu(engine, selectedSlot));
                }
            } else {
                // Kehre zurück zum SaveSlotScreen
                engine.popScreen();
            }
        }
    }
}
