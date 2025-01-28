package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class EnterNameMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final int selectedSlot;
    private String playerName;
    private final float startY;

    public EnterNameMenu(Engine engine, int selectedSlot) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedSlot = selectedSlot;
        this.playerName = ""; // Spielername starten als leer
        this.startY = 300;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();


        textRenderer.drawCenteredText("Enter your name:", startY, this.engine.getFont());
        textRenderer.drawCenteredText(playerName + "_", startY - 50, this.engine.getFont()); // Blinken des Cursors simuliert
        textRenderer.drawCenteredText("Press ESC to cancel.", startY - 100, this.engine.getFont()); // Hinweis zum Abbrechen

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {

        // Zeichen eingeben
        for (char c = 'A'; c <= 'Z'; c++) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(c)))) {
                playerName += c; // Zeichen hinzufügen
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !playerName.isEmpty()) {
            playerName = playerName.substring(0, playerName.length() - 1); // Letztes Zeichen entfernen
        }

        // Wechsel zur Bestätigungsansicht
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !playerName.isEmpty()) {
            engine.pushScreen(new ConfirmSelection(
                    engine,
                    "Do you want to use the name '" + playerName + "'?",
                    () -> {
                        // Aktion für "Ja"
                        engine.onPlayerNameConfirmed(playerName, selectedSlot);
                    },
                    () -> {
                        // Aktion für "Nein"
                    }
            ));

        }

        // Abbrechen der Namenseingabe
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen(); // Kehre zur vorherigen Szene zurück
        }

    }
}
