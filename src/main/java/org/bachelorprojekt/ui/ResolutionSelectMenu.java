package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

import java.util.List;
import java.util.stream.Collectors;

import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

/**
 * A menu screen that allows the user to select a screen resolution from predefined options.
 * The user can navigate through the list of available resolutions and select one to apply,
 * or go back to the previous screen.
 */
public class ResolutionSelectMenu extends ScreenAdapter {

    /**
     * Represents a screen resolution with width and height.
     */
    private class Resolution {
        int width;
        int height;

        /**
         * Constructs a new Resolution object.
         *
         * @param width  the width of the resolution
         * @param height the height of the resolution
         */
        public Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Returns the string representation of the resolution in the format "width x height".
         *
         * @return the resolution as a string
         */
        @Override
        public String toString() {
            return width + "x" + height;
        }
    }

    private final Resolution[] resolutions = {
        new Resolution(1280, 720),
        new Resolution(1536, 864),
        new Resolution(1920, 1080),
        new Resolution(2560, 1440),
        new Resolution(3840, 2160),
    };
    private List<String> resStrings = List.of(this.resolutions).stream().map(Object::toString).collect(Collectors.toList());
    private int selectedOption;
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final float startY;

    /**
     * Constructs a new ResolutionSelectMenu.
     *
     * @param engine the Engine instance used to interact with the game system
     */
    public ResolutionSelectMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.startY = 280;
        this.selectedOption = 0;
        this.resStrings.add("Back");
    }

    /**
     * Renders the menu screen. This method is responsible for drawing the resolution options and handling input.
     *
     * @param delta the time in seconds since the last render call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Draw the title
        textRenderer.drawCenteredText("Select Resolution", 500, this.engine.getFont());

        // Draw the resolution options
        for (int i = 0; i < resStrings.size(); i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + resStrings.get(i), startY - i * 30, this.engine.getFont());
            } else {
                textRenderer.drawCenteredText(resStrings.get(i), startY - i * 30, this.engine.getFont());
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    /**
     * Handles user input for navigating the menu and selecting a resolution.
     */
    private void handleInput() {
        // Navigate down
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % resStrings.size();
        }

        // Navigate up
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + resStrings.size()) % resStrings.size();
        }

        // Confirm selection
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption < resolutions.length) { // Select a resolution
                handleResolutionSelection(selectedOption);
            } else if (selectedOption == resolutions.length) { // Go back
                engine.popScreen();
            }
        }
    }

    /**
     * Applies the selected resolution by resizing the game window.
     *
     * @param selection the index of the selected resolution
     */
    private void handleResolutionSelection(int selection) {
        int width = resolutions[selection].width;
        int height = resolutions[selection].height;
        engine.resize(width, height);
    }

    @Override
    public void show() {
        // Called when the screen is displayed
    }

    @Override
    public void hide() {
        // Called when the screen is hidden
    }

    @Override
    public void dispose() {
        System.out.println("ResolutionSelectMenu resources disposed.");
    }

    @Override
    public void resize(int width, int height) {
        // Called when the window is resized
    }

    @Override
    public void pause() {
        // Called when the game is paused
    }

    @Override
    public void resume() {
        // Called when the game is resumed
    }
}
