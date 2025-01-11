package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private String currentText = "Willkommen in deinem Abenteuer! Drücke 'E', um zu starten.";
    private String displayedTime = ""; // Zeit, die angezeigt wird
    private boolean showColon = true; // Blinken des Doppelpunkts
    private float timeAccumulator = 0; // Zeitakku für das Blinken der Uhr

    private boolean isPaused = false; // Zustand des Spiels (Pausiert oder nicht)
    private String[] menuOptions = {"Resume", "Settings", "Leave"};
    private int selectedOption = 0; // Aktuell ausgewählte Menüoption

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Standard-Schriftart
    }

    @Override
    public void render() {
        // Bildschirm löschen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isPaused) {
            renderPauseMenu();
        } else {
            // Spielinhalte rendern
            updateClock(Gdx.graphics.getDeltaTime());

            batch.begin();
            font.draw(batch, currentText, 50, Gdx.graphics.getHeight() - 50);
            font.draw(batch, "Uhrzeit: " + displayedTime, 50, Gdx.graphics.getHeight() - 100);
            batch.end();

            handleInput();
        }
    }

    private void updateClock(float delta) {
        // Zeit sammeln
        timeAccumulator += delta;

        // Jede Sekunde die Zeit aktualisieren
        if (timeAccumulator >= 1.0f) {
            // Hole die aktuelle Uhrzeit
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = showColon
                    ? DateTimeFormatter.ofPattern("HH:mm")
                    : DateTimeFormatter.ofPattern("HH mm");
            displayedTime = now.format(formatter);

            // Blink-Status wechseln
            showColon = !showColon;

            // Zeitakku zurücksetzen
            timeAccumulator -= 1.0f;
        }
    }

    private void renderPauseMenu() {
        batch.begin();

        font.draw(batch, "Pause Menü", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 50);
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                font.draw(batch, "> " + menuOptions[i], Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100 - i * 30);
            } else {
                font.draw(batch, menuOptions[i], Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100 - i * 30);
            }
        }

        batch.end();

        handlePauseMenuInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = true; // Spiel pausieren
        }
    }

    private void handlePauseMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            executeMenuOption();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            for (int i = 0; i < menuOptions.length; i++) {
                if (mouseY > Gdx.graphics.getHeight() - 100 - i * 30 - 20 && mouseY < Gdx.graphics.getHeight() - 100 - i * 30 + 10) {
                    selectedOption = i;
                    executeMenuOption();
                    break;
                }
            }
        }
    }

    private void executeMenuOption() {
        switch (selectedOption) {
            case 0: // Resume
                isPaused = false;
                break;
            case 1: // Settings
                currentText = "Settings-Menü noch nicht implementiert.";
                isPaused = false;
                break;
            case 2: // Leave
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Textadventure mit Uhr");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}
