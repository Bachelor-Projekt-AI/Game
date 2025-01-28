package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.bachelorprojekt.util.Engine;

public class Main extends ApplicationAdapter {


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Bachelorprojekt");
        config.setWindowedMode(800, 600);
        config.setResizable(false);

        new Lwjgl3Application(new Engine(), config);
    }

}