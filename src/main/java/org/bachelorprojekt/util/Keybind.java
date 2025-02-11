package org.bachelorprojekt.util;

public class Keybind {
	private final int key;
	private final String description;
	private final Runnable action;

	public Keybind(int key, String description, Runnable action) {
		this.key = key;
		this.description = description;
		this.action = action;
	}

	public int getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public Runnable getAction() {
		return action;
	}
}
