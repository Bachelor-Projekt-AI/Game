package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

/**
 * The PauseMenu class represents the in-game pause menu.
 * It allows the player to pause the game and perform actions like resuming the game,
 * saving the game, adjusting the resolution, and quitting to the main menu.
 */
public class PauseMenu extends ScreenAdapter {
    
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String[] menuOptions = {"Resume", "Save Game", "Options", "Quit to Main Menu"};
    private int selectedOption;
    private final float startY;

    /**
     * Constructs a new PauseMenu.
     * 
     * @param engine The engine that drives the game logic and rendering.
     */
    public PauseMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.selectedOption = 0; // Default to the first option
        this.startY = 300; // Y-position for the menu
    }

    /**
     * Renders the pause menu on the screen.
     * It clears the screen, then draws the menu title and options.
     * The selected option is highlighted.
     * 
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Draw the title of the pause menu
        textRenderer.drawCenteredText("Paused", startY, this.engine.getFont());

        // Render the menu options
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + menuOptions[i], startY - (i + 1) * 30 - 15, this.engine.getFont());
            } else {
                textRenderer.drawCenteredText(menuOptions[i], startY - (i + 1) * 30 - 15, this.engine.getFont());
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    /**
     * Handles the input from the user for navigating and selecting menu options.
     * 
     * The user can move up or down through the options and confirm the selection.
     * If the user presses ESC, the menu will close and the game will resume.
     */
    private void handleInput() {
        // Navigate up through the options
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        }

        // Navigate down through the options
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        }

        // Confirm the selected option
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedOption) {
                case 0: // Resume
                    engine.popScreen(); // Resume the game
                    break;
                case 1: // Save Game
                    saveGame(); // Trigger save game logic
                    break;
                case 2: // Options
                    engine.pushScreen(new ResolutionSelectMenu(engine)); // Open resolution settings
                    break;
                case 3: // Quit to Main Menu
                    engine.quitToMenu(); // Quit and return to the main menu
                    break;
            }
        }

        // Close the menu if ESC is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen(); // Resume the game
        }
    }

    /**
     * Saves the game state.
     * This function currently only prints a message to the console.
     * Implement the actual saving logic here to persist the game state.
     */
    private void saveGame() {
        // Save the game state (this is a placeholder implementation)
        // Implement your saving mechanism here
        System.out.println("Game saved.");
    }
}
