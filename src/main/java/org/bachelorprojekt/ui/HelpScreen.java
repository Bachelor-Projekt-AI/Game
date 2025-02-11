package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.Keybind;

import java.util.List;

public class HelpScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final List<Keybind> keybinds;

    public HelpScreen(Engine engine, List<Keybind> keybinds) {
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.keybinds = keybinds;
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Abdunkelung des Hintergrunds
		int line = 0;
		for (Keybind keybind : keybinds.reversed()) {
			font.draw(batch, keybind.getFullDescription(), 1400, ++line * 30);
		}

        batch.end();
        handleInput();
    }

    private void handleInput() {
		for (Keybind keybind : keybinds) {
			if (Gdx.input.isKeyJustPressed(keybind.getKey())) {
				keybind.getAction().run();
				return;
			}
		}
    }
}
