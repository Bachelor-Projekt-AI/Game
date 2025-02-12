package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

/**
 * The EnterNameMenu class represents a screen where the player can enter their name.
 * The user can type letters, delete the last character, confirm the name, or cancel the operation.
 */
public class EnterNameMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final int selectedSlot;
    private String playerName;
    private final float startY;

    /**
     * Creates an instance of the EnterNameMenu screen.
     * 
     * @param engine The game engine responsible for managing game systems.
     * @param selectedSlot The selected save slot for the player's name.
     */
    public EnterNameMenu(Engine engine, int selectedSlot) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedSlot = selectedSlot;
        this.playerName = ""; // Initialize player name as empty
        this.startY = 300; // Y-Position for text rendering
    }

    /**
     * Renders the name input screen, displaying the player's current input and options.
     * 
     * @param delta The time in seconds since the last render call.
     */
    @Override
    public void render(float delta) {
        // Clear the screen with a black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Display instructions and player's current input
        textRenderer.drawCenteredText("Enter your name:", startY, this.engine.getFont());
        textRenderer.drawCenteredText(playerName + "_", startY - 50, this.engine.getFont()); // Simulate cursor blinking
        textRenderer.drawCenteredText("Press ESC to cancel.", startY - 100, this.engine.getFont());

        engine.getBatch().end();

        handleInput();
    }

    /**
     * Handles user input for the name entry. Allows typing, backspacing, confirming, and cancelling the input.
     */
    private void handleInput() {
        // Input for each letter A-Z
        for (char c = 'A'; c <= 'Z'; c++) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(c)))) {
                playerName += c; // Append character to player name
            }
        }

        // Backspace to remove the last character
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !playerName.isEmpty()) {
            playerName = playerName.substring(0, playerName.length() - 1); // Remove last character
        }

        // Confirm the entered name
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !playerName.isEmpty()) {
            engine.pushScreen(new ConfirmSelection(
                    engine,
                    "Do you want to use the name '" + playerName + "'?",
                    () -> {
                        // Action for "Yes"
                        engine.onPlayerNameConfirmed(playerName, selectedSlot);
                    },
                    () -> {
                        // Action for "No"
                    }
            ));
        }

        // Cancel name entry
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen(); // Return to previous screen
        }
    }
}
