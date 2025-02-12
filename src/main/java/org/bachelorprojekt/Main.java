package org.bachelorprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.bachelorprojekt.util.Engine;

/**
 * The entry point of the application, responsible for initializing and launching the game.
 * It configures the application window and starts the game engine.
 */
public class Main extends ApplicationAdapter {
    
    /**
     * The main method that initializes the game and starts the application.
     * 
     * @param args Command-line arguments passed to the application (not used in this case).
     */
    public static void main(String[] args) {
        // Configuring the application window
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Bachelorprojekt"); // Set the window title
        config.setWindowedMode(1920, 1080); // Set the window size (1920x1080)
        config.setResizable(false); // Disable window resizing

        // Launch the application with the Engine as the game logic
        new Lwjgl3Application(new Engine(), config);
    }
}
