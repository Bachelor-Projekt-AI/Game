package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.bachelorprojekt.util.Engine;
/**
 * Die Hauptklasse des Spiels, die den Einstiegspunkt der Anwendung darstellt.
 * Sie konfiguriert das Fenster und startet die Anwendung mit der Spielengine.
 */
public class Main extends ApplicationAdapter {

    /**
     * Der Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet).
     */
    public static void main(String[] args) {
        // Konfiguration der LWJGL3-Anwendung
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Bachelorprojekt");
        config.setWindowedMode(1920, 1080);
        config.setResizable(false);

        // Startet die Anwendung mit der definierten Engine
        new Lwjgl3Application(new Engine(), config);
    }
}
