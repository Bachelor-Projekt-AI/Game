package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private boolean isMainMenu = true; // Zustand des Hauptmenüs
    private boolean isMapOpen = false; // Zustand der Map
    private String[] menuOptions = {"Resume", "Settings", "Leave"};
    private String[] mainMenuOptions = {"Start Game", "Settings", "Exit"};
    private int selectedOption = 0; // Aktuell ausgewählte Menüoption

    private String currentTextChap1 = "";
    private String fullText = "Chapter 1\nThe Call to Adventure";
    private float textTimer = 0;
    private int currentCharIndex = 0;

    private boolean isAnimatingText = true;

    private BitmapFont monospaceFont;

    private void loadMonospaceFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("src/main/resources/fonts/JetBrainsMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14; // Schriftgröße
        parameter.mono = true; // Monospace aktivieren (falls möglich)
        monospaceFont = generator.generateFont(parameter);
        generator.dispose(); // Generator nach Verwendung freigeben
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Standard-Schriftart
        loadMonospaceFont();
    }

    @Override
    public void render() {
        // Bildschirm löschen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        if (isMainMenu) {
            renderMainMenu();
        } else if (isMapOpen) {
            renderMap();
        } else if (isPaused) {
            renderPauseMenu();
        } else {
            // Spielinhalte rendern
            batch.begin();
            if (isAnimatingText) {
                animateText(Gdx.graphics.getDeltaTime());
                drawCenteredText(currentText);
            } else {
                // Weitere Inhalte des Spiels hier einfügen
                font.draw(batch, "Press ENTER to continue", Gdx.graphics.getWidth() / 2f - 100, 100);
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // Start des Spiels nach der Animation
                    startGame();
                }
            }
            batch.end();
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

    private void renderMainMenu() {
        batch.begin();

        font.draw(batch, "Main Menu", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 50);
        for (int i = 0; i < mainMenuOptions.length; i++) {
            if (i == selectedOption) {
                font.draw(batch, "> " + mainMenuOptions[i], Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100 - i * 30);
            } else {
                font.draw(batch, mainMenuOptions[i], Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 100 - i * 30);
            }
        }

        batch.end();

        handleMainMenuInput();
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

    private void renderMap() {
        batch.begin();

        monospaceFont.draw(batch, "Map - Chapter 1", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 50);

        String[] map = {
                "                       ,-.^._                 _\n" +
                        "                     .'      `-.            ,' ;\n" +
                        "          /`-.  ,----'         `-.   _  ,-.,'  `\n" +
                        "       _.'   `--'                 `-' '-'      ;\n" +
                        "      :                         o             ;    __,-.\n" +
                        "      ,'    o               Blacksmith       ;_'-',.__'--.\n" +
                        "     :    Healer                            ,---``    `--'\n" +
                        "     :                                      ;\n" +
                        "     :                                      :\n" +
                        "     ;                                      :\n" +
                        "    (                                       ;\n" +
                        "     `-.                           *      ,'\n" +
                        "       ;                          You    :\n" +
                        "     .'                             .-._,'\n" +
                        "   .'                               `.\n" +
                        "_.'                                .__;\n" +
                        "`._                  o            ;\n" +
                        "   `.            Innkeeper       :\n" +
                        "     `.               ,..__,---._;\n" +
                        "       `-.__         :\n" +
                        "            `.--.____;\n"
        };

        for (int i = 0; i < map.length; i++) {
            monospaceFont.draw(batch, map[i], 150, Gdx.graphics.getHeight() - 100 - i * 20);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isMapOpen = false; // Close the map
        }
    }

    private void handleMainMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + mainMenuOptions.length) % mainMenuOptions.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % mainMenuOptions.length;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            executeMainMenuOption();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            for (int i = 0; i < mainMenuOptions.length; i++) {
                if (mouseY > Gdx.graphics.getHeight() - 100 - i * 30 - 20 && mouseY < Gdx.graphics.getHeight() - 100 - i * 30 + 10) {
                    selectedOption = i;
                    executeMainMenuOption();
                    break;
                }
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = true; // Spiel pausieren
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isMapOpen = true; // Open the map
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

    private void executeMainMenuOption() {
        switch (selectedOption) {
            case 0: // Start Game
                isMainMenu = false;
                break;
            case 1: // Settings
                currentText = "Settings-Menü noch nicht implementiert.";
                break;
            case 2: // Exit
                Gdx.app.exit();
                break;
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

    private void animateText(float delta) {
        textTimer += delta;

        // Buchstabe für Buchstabe Animation
        if (textTimer >= 0.1f && currentCharIndex < fullText.length()) {
            currentTextChap1 += fullText.charAt(currentCharIndex);
            currentCharIndex++;
            textTimer = 0;
        }

        // Animation fertig
        if (currentCharIndex >= fullText.length()) {
            isAnimatingText = false;
        }
    }

    private void drawCenteredText(String text) {
        String[] lines = text.split("\\n");
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        for (int i = 0; i < lines.length; i++) {
            float textWidth = font.getRegion().getRegionWidth() / (float) font.getRegion().getRegionHeight() * lines[i].length();
            font.draw(batch, lines[i], centerX - textWidth / 2f, centerY - (i * 30));
        }
    }

    private void startGame() {
        currentText = "The game starts here!"; // Beispiel für den Übergang ins Spiel
        isAnimatingText = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("The Flame of Life");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}