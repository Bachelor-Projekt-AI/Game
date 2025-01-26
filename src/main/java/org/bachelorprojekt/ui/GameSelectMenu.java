package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.game.ChapterOne;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class GameSelectMenu extends ScreenAdapter {

    private final String[] saveSlots = {"Save Slot 1", "Save Slot 2", "Save Slot 3", "Back"};
    private int selectedOption;
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final GameStateManager gameStateManager;
    private final float startY;

    public GameSelectMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.gameStateManager = engine.getGameStateManager();
        this.startY = 280;
        this.selectedOption = 0;
    }

    @Override
    public void render(float delta) {
        // Hintergrund löschen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Titel zeichnen
        textRenderer.drawCenteredText("Select Save Slot", 500);

        // Save Slots anzeigen
        for (int i = 0; i < saveSlots.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + saveSlots[i], startY - i * 30);
            } else {
                textRenderer.drawCenteredText(saveSlots[i], startY - i * 30);
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
        // Nach unten navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % saveSlots.length;
        }

        // Nach oben navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + saveSlots.length) % saveSlots.length;
        }

        // Auswahl bestätigen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption < 3) { // Save Slot 1, 2 oder 3
                handleSaveSlotSelection(selectedOption);
            } else if (selectedOption == 3) { // Zurück
                engine.popScreen();
            }
        }
    }

    private void handleSaveSlotSelection(int slot) {
        // Prüfen, ob der Save Slot belegt ist (z. B. über die DB)
        boolean saveExists = checkSaveExists(slot);

        if (saveExists) {
            // Spielstand aus der DB laden
            // gameStateManager.loadGameFromSlot(slot);
            System.out.println("Spielstand aus Slot " + (slot + 1) + " geladen.");
            // engine.pushScreen(new ChapterOne(engine)); // Gehe zum gespeicherten Kapitel
        } else {
            // Neues Spiel starten
            System.out.println("Neuer Spielstand in Slot " + (slot + 1) + " erstellt.");
            // gameStateManager.startNewGame(slot);
            engine.pushScreen(new ConfirmSelection(engine, slot, false)); // Starte neues Spiel
        }
    }

    private boolean checkSaveExists(int slot) {
        // Beispiel: Überprüfe, ob der Spielstand in der DB existiert
        // Dies kann durch Abfragen der SQLite-Datenbank geschehen
        return false;
        //return engine.getDatabaseHandler().doesSaveExist(slot);
    }

    @Override
    public void show() {
        // Wird aufgerufen, wenn der Screen angezeigt wird
    }

    @Override
    public void hide() {
        // Wird aufgerufen, wenn der Screen verborgen wird
    }

    @Override
    public void dispose() {
        System.out.println("SaveSlotScreen resources disposed.");
    }

    @Override
    public void resize(int width, int height) {
        // Wird aufgerufen, wenn das Fenster neu skaliert wird
    }

    @Override
    public void pause() {
        // Wird aufgerufen, wenn das Spiel pausiert wird
    }

    @Override
    public void resume() {
        // Wird aufgerufen, wenn das Spiel fortgesetzt wird
    }
}
