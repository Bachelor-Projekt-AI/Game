package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
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
        	font.draw(batch, keyString(keybind.getKey()) + keybind.getDescription(), 1400, ++line * 30);
		}

        batch.end();
        handleInput();
    }

	private String keyString(int key) {
		switch (key) {
			case Keys.ESCAPE: return "  (ESC) ";
			case Keys.UP: return "   (UP) ";
			case Keys.DOWN: return " (DOWN) ";
			case Keys.LEFT: return " (LEFT) ";
			case Keys.RIGHT: return "(RIGHT) ";
			case Keys.ENTER: return "(ENTER) ";
			case Keys.C: return "    (C) ";
			case Keys.E: return "    (E) ";
			case Keys.K: return "    (K) ";
			case Keys.M: return "    (M) ";
			case Keys.Q: return "    (Q) ";
			default: return "";
		}
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
