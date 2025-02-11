package org.bachelorprojekt.util;

import com.badlogic.gdx.Input.Keys;

public class Keybind {
	private final int key;
	private final String fullDescription;
	private final Runnable action;
	private final int PADDED = 5;

	public Keybind(int key, String description, Runnable action) {
		this.key = key;
		this.fullDescription = keyString(key) + description;
		this.action = action;
	}

	private String keyString(int key) {
		String name = keyName(key);
		String padding = "";
		for (int i = 0; i < PADDED - name.length(); i++) {
			padding += " ";
		}
		return padding + "(" + name + ") ";
	}

	private String keyName(int key) {
		switch (key) {
			case Keys.ESCAPE: return "ESC";
			case Keys.UP: return "UP";
			case Keys.DOWN: return "DOWN";
			case Keys.LEFT: return "LEFT";
			case Keys.RIGHT: return "RIGHT";
			case Keys.ENTER: return "ENTER";
			case Keys.BACKSPACE: return "BACK";
			case Keys.C: return "C";
			case Keys.E: return "E";
			case Keys.K: return "K";
			case Keys.M: return "M";
			case Keys.Q: return "Q";
			default: return "";
		}
	}

	public int getKey() {
		return key;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public Runnable getAction() {
		return action;
	}
}
