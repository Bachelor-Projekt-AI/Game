package org.bachelorprojekt.util;

public abstract class Scene {

    protected final Engine engine;

    public Scene(Engine engine) {
        this.engine = engine;
    }

    public abstract void render();

    protected abstract void handleInput();
}
