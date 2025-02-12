package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

/**
 * Represents a confirmation selection screen that prompts the player with a message
 * and two options: "Yes" or "No". The player can navigate between the options and confirm
 * their choice using the ENTER key or cancel using the ESC key.
 */
public class ConfirmSelection extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String message; // The message displayed to the player
    private final Runnable onConfirm; // Action to perform when "Yes" is selected
    private final Runnable onCancel; // Action to perform when "No" is selected
    private int selectedOption; // 0 = Yes, 1 = No
    private final float startY; // Y position for displaying the options
    private final ChapterScreen chapter; // Optional ChapterScreen to render behind the confirmation

    /**
     * Constructs a confirmation selection screen with a message and actions for "Yes" and "No".
     *
     * @param engine The game engine instance for rendering and screen management.
     * @param message The message to display in the confirmation prompt.
     * @param onConfirm The action to run when "Yes" is selected.
     * @param onCancel The action to run when "No" is selected.
     */
    public ConfirmSelection(Engine engine, String message, Runnable onConfirm, Runnable onCancel) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.message = message;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.selectedOption = 0; // Default option is "Yes"
        this.startY = 300;
        this.chapter = null;
    }

    /**
     * Constructs a confirmation selection screen with a message, actions for "Yes" and "No",
     * and an optional ChapterScreen to render behind the confirmation.
     *
     * @param engine The game engine instance for rendering and screen management.
     * @param message The message to display in the confirmation prompt.
     * @param onConfirm The action to run when "Yes" is selected.
     * @param onCancel The action to run when "No" is selected.
     * @param chapter The optional ChapterScreen to render behind the confirmation prompt.
     */
    public ConfirmSelection(Engine engine, String message, Runnable onConfirm, Runnable onCancel, ChapterScreen chapter) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.message = message;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.selectedOption = 0; // Default option is "Yes"
        this.startY = 300;
        this.chapter = chapter;
    }

    /**
     * Renders the confirmation screen, displaying the message and the options for the user to choose from.
     *
     * @param delta The time elapsed since the last frame, used for animations (not used in this method).
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If there is a ChapterScreen, render it behind the confirmation dialog
        if (chapter != null) {
            chapter.draw();
        }

        engine.getBatch().begin();

        // Display the message
        textRenderer.drawCenteredText(message, startY, this.engine.getFont());

        // Display the options ("Yes" and "No")
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

    /**
     * Handles the input from the player to navigate between options and select an option.
     */
    private void handleInput() {
        // Navigate between "Yes" and "No"
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption == 0) ? 1 : 0; // Toggle between Yes and No
        }

        // Confirm the selection
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            engine.popScreen();
            if (selectedOption == 0) {
                System.out.println("Yes");
                onConfirm.run(); // "Yes" selected
            } else {
                System.out.println("No");
                onCancel.run(); // "No" selected
            }
        }

        // Cancel with ESC key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen();
            onCancel.run(); // Run the cancel action
        }
    }
}
