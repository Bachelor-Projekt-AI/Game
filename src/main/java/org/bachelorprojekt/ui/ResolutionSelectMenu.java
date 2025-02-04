package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;

import java.util.List;

import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameStateManager;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class ResolutionSelectMenu extends ScreenAdapter {
	private class Resolution {
		int width;
		int height;

		public Resolution(int width, int height) {
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString() {
			return width + "x" + height;
		}
	}

    private final Resolution[] resolutions = {
		new Resolution(640, 360),
		new Resolution(768, 432),
		new Resolution(1024, 576),
		new Resolution(1280, 720),
		new Resolution(1536, 864),
		new Resolution(1920, 1080),
		new Resolution(2560, 1440),
		new Resolution(3840, 2160),
	};
	private List<String> resStrings = List.of(this.resolutions).stream().map(Object::toString).collect(java.util.stream.Collectors.toList());
    private int selectedOption;
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final GameStateManager gameStateManager;
    private final float startY;

    public ResolutionSelectMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.gameStateManager = engine.getGameStateManager();
        this.startY = 280;
        this.selectedOption = 0;
		this.resStrings.add("Back");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.getBatch().begin();

        // Titel zeichnen
        textRenderer.drawCenteredText("Select Resolution", 500, this.engine.getFont());

        // Save Slots anzeigen
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

    private void handleInput() {
        // Nach unten navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % resStrings.size();
        }

        // Nach oben navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + resStrings.size()) % resStrings.size();
        }

        // Auswahl bestätigen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption < resolutions.length) { // Save Slot 1, 2 oder 3
                handleResolutionSelection(selectedOption);
            } else if (selectedOption == resolutions.length) { // Zurück
                engine.popScreen();
            }
        }
    }

    private void handleResolutionSelection(int selection) {
		int width = resolutions[selection].width;
		int height = resolutions[selection].height;
        // viewport.update(width, height, true);
		// screenStack.peek().resize(width, height);
		engine.resize(width, height);
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
        System.out.println("ResolutionSelectMenu resources disposed.");
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
