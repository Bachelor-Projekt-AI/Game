package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;



public class Main extends ApplicationAdapter {

    @Override
    public void create() {
        System.out.println("LibGDX läuft!");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Bildschirm schwarz färben
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        System.out.println("LibGDX wird geschlossen.");
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mein Spiel");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new Main(), config);
    }
}
