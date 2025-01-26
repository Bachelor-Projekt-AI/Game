package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterOne;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class EnterNameMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final int selectedSlot;
    private String playerName;
    private boolean confirmName;
    private int selectedOption; // 0 = Yes, 1 = No
    private final float startY;

    public EnterNameMenu(Engine engine, int selectedSlot) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedSlot = selectedSlot;
        this.playerName = ""; // Spielername starten als leer
        this.confirmName = false; // Initial ist der Name nicht bestätigt
        this.selectedOption = 0; // Standardmäßig auf "Yes"
        this.startY = 300;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        if (!confirmName) {
            // Zeige die Namenseingabe an
            textRenderer.drawCenteredText("Enter your name:", startY);
            textRenderer.drawCenteredText(playerName + "_", startY - 50); // Blinken des Cursors simuliert
            textRenderer.drawCenteredText("Press ESC to cancel.", startY - 100); // Hinweis zum Abbrechen
        } else {
            // Zeige die Bestätigungsansicht
            textRenderer.drawCenteredText("Confirm name: " + playerName, startY);

            // Render die Auswahl mit Markierung
            if (selectedOption == 0) {
                textRenderer.drawCenteredText("> Yes", startY - 50);
                textRenderer.drawCenteredText("No", startY - 80);
            } else {
                textRenderer.drawCenteredText("Yes", startY - 50);
                textRenderer.drawCenteredText("> No", startY - 80);
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
        if (!confirmName) {
            // Zeichen eingeben
            for (char c = 'A'; c <= 'Z'; c++) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(c)))) {
                    playerName += c; // Zeichen hinzufügen
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && playerName.length() > 0) {
                playerName = playerName.substring(0, playerName.length() - 1); // Letztes Zeichen entfernen
            }

            // Wechsel zur Bestätigungsansicht
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !playerName.isEmpty()) {
                confirmName = true;
            }

            // Abbrechen der Namenseingabe
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                engine.popScreen(); // Kehre zur vorherigen Szene zurück
            }
        } else {
            // Auswahl zwischen Yes und No
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedOption = (selectedOption == 0) ? 1 : 0; // Umschalten zwischen Yes und No
            }

            // Bestätigung der Auswahl
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (selectedOption == 0) { // Yes
                    // Neuer Spieler und GameStateManager erstellen
                    Player player = new Player(playerName);
                    engine.setGameStateManager(new GameStateManager(engine, player));
                    // Starte ein neues Spiel
                    engine.pushScreen(new ChapterOne(engine, true));
                } else { // No
                    confirmName = false; // Kehre zur Namenseingabe zurück
                }
            }

            // Abbrechen der Namenseingabe in der Bestätigungsansicht
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                confirmName = false; // Kehre zurück zur Namenseingabe
            }
        }
    }
}
