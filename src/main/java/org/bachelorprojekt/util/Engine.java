package org.bachelorprojekt.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.ui.LoadingScreen;
import org.bachelorprojekt.ui.Menu;
import org.bachelorprojekt.ui.MessageScreen;

import java.util.Stack;

/**
 * Engine class that handles game state, screen management, font rendering, notifications, and player data.
 * It extends the Game class from libGDX, providing a game framework with various utility functions.
 */
public class Engine extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private GameSystemManager gameSystemManager;
    private TextRenderer textRenderer;
    private final Stack<Screen> screenStack;
    private Texture whiteTexture;
    private boolean notificationActive = false;

    /**
     * Returns whether a notification is currently active.
     * 
     * @return true if a notification is active, false otherwise
     */
    public boolean isNotificationActive() {
        return notificationActive;
    }

    /**
     * Sets the notification status.
     * 
     * @param notificationActive true to activate a notification, false to deactivate
     */
    public void setNotificationActive(boolean notificationActive) {
        this.notificationActive = notificationActive;
    }

    /**
     * Sends a notification with the given message, pushing a MessageScreen onto the stack.
     * 
     * @param message the notification message to display
     */
    public void sendNotification(String message) {
        pushScreen(new MessageScreen(this, message));
    }

    /**
     * Returns the stack of screens currently managed by the engine.
     * 
     * @return the screen stack
     */
    public Stack<Screen> getScreenStack() {
        return screenStack;
    }

    /**
     * Constructs an Engine instance with an empty screen stack.
     */
    public Engine() {
        this.screenStack = new Stack<>();
    }

    /**
     * Pushes a new screen onto the stack and sets it as the active screen.
     * 
     * @param screen the screen to push and set
     */
    public void pushScreen(Screen screen) {
        screenStack.push(screen);
        setScreen(screen);
    }

    /**
     * Pops the top screen from the stack, disposing of it and setting the previous screen as active.
     */
    public void popScreen() {
        if (!screenStack.isEmpty()) {
            Screen oldScreen = screenStack.pop();
            oldScreen.dispose();

            if (!screenStack.isEmpty()) {
                setScreen(screenStack.peek());
            } else {
                System.out.println("No more screens in stack.");
            }
        }
    }

    /**
     * Clears all screens from the stack and pushes the main menu screen.
     */
    public void quitToMenu() {
        clearScreens();
        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    /**
     * Clears all screens from the stack, disposing of them.
     */
    public void clearScreens() {
        while (!screenStack.isEmpty()) {
            Screen screen = screenStack.pop();
            screen.dispose();
        }
    }

    /**
     * Creates a white texture used for screen transitions or backgrounds.
     */
    private void createWhiteTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1); // White color
        pixmap.fill();
        whiteTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    /**
     * Returns the white texture created by the engine.
     * 
     * @return the white texture
     */
    public Texture getWhiteTexture() {
        return whiteTexture;
    }

    /**
     * Initializes the engine, setting up the sprite batch, font, and text renderer.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = loadFont("fonts/JetBrainsMono-Regular.ttf", 26);
        textRenderer = new TextRenderer(this);

        Gdx.graphics.setForegroundFPS(60);

        createWhiteTexture();

        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    /**
     * Resizes the game window when the window size changes.
     * 
     * @param width the new width of the window
     * @param height the new height of the window
     */
    @Override
    public void resize(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
    }

    /**
     * Renders the game. This method is called every frame.
     */
    @Override
    public void render() {
        super.render();
        if (gameSystemManager != null) {
            gameSystemManager.getChapterSystem().update();
        }
    }

    /**
     * Disposes of the engine resources, including sprite batch, font, and screens.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        clearScreens();
    }

    /**
     * Loads a custom font from a file with the specified size.
     * 
     * @param fontPath the path to the font file
     * @param fontSize the desired font size
     * @return the loaded BitmapFont
     */
    public BitmapFont loadFont(String fontPath, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize; // Set the font size
        parameter.mono = true; // Use a monospaced font
        BitmapFont customFont = generator.generateFont(parameter);
        setAllFixedWidth(customFont); // Set all characters to a fixed width
        generator.dispose(); // Dispose the generator to avoid memory leaks
        return customFont;
    }

    /**
     * Sets all characters in the font to have a fixed width.
     * 
     * @param font the BitmapFont to modify
     */
    public static void setAllFixedWidth(BitmapFont font) {
        BitmapFont.BitmapFontData data = font.getData();
        int maxAdvance = 0;
        for (int index = 0, end = 65536; index < end; index++) {
            BitmapFont.Glyph g = data.getGlyph((char) index);
            if (g != null && g.xadvance > maxAdvance) maxAdvance = g.xadvance;
        }
        for (int index = 0, end = 65536; index < end; index++) {
            BitmapFont.Glyph g = data.getGlyph((char) index);
            if (g == null) continue;
            g.xoffset += (maxAdvance - g.xadvance) / 2;
            g.xadvance = maxAdvance;
            g.kerning = null;
            g.fixedWidth = true;
        }
    }

    /**
     * Returns a special font used for specific game UI elements.
     * 
     * @return the special BitmapFont
     */
    public static BitmapFont getSpecialFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JetBrainsMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:'\",<.>/?\\`~■"; // Add all needed characters here
        parameter.size = 26;
        BitmapFont font = generator.generateFont(parameter);
        setAllFixedWidth(font);
        generator.dispose();
        return font;
    }

    /**
     * Returns the current font used by the engine.
     * 
     * @return the current BitmapFont
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Returns the SpriteBatch used for drawing 2D elements.
     * 
     * @return the SpriteBatch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Exits the game.
     */
    public void exit() {
        Gdx.app.exit();
    }

    /**
     * Returns the GameSystemManager associated with the engine.
     * 
     * @return the GameSystemManager
     */
    public GameSystemManager getGameSystemManager() {
        return gameSystemManager;
    }

    /**
     * Sets the GameSystemManager for the engine.
     * 
     * @param gameSystemManager the GameSystemManager to set
     */
    public void setGameSystemManager(GameSystemManager gameSystemManager) {
        this.gameSystemManager = gameSystemManager;
    }

    /**
     * Returns the TextRenderer used by the engine.
     * 
     * @return the TextRenderer
     */
    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    /**
     * Starts the game by confirming the player name and selected slot.
     * 
     * @param playerName the name of the player
     * @param selectedSlot the selected save slot
     */
    public void onPlayerNameConfirmed(String playerName, int selectedSlot) {

        System.out.println("Player name confirmed: " + playerName);

        // Start a new game with the player
        Player player = new Player(playerName, 100, 100, 1000);

        pushScreen(new LoadingScreen(this, player, selectedSlot));
    }
}
