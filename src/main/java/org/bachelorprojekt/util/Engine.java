package org.bachelorprojekt.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.ui.LoadingScreen;
import org.bachelorprojekt.ui.Menu;

import java.util.Stack;

public class Engine extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;

    private GameSystemManager gameSystemManager;
    private TextRenderer textRenderer;
    private final Stack<Screen> screenStack;

    public Engine() {
        this.screenStack = new Stack<>();
    }

    public void pushScreen(Screen screen) {
        screenStack.push(screen);
        setScreen(screen);
    }

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

    public void quitToMenu() {
        clearScreens();
        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    public void clearScreens() {
        while (!screenStack.isEmpty()) {
            Screen screen = screenStack.pop();
            screen.dispose();
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = loadFont("fonts/JetBrainsMono-Regular.ttf", 26);
        viewport = new FitViewport(1920, 1080);
        viewport.apply();
        textRenderer = new TextRenderer(this);

        Gdx.graphics.setForegroundFPS(60);

        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    @Override
    public void resize(int width, int height) {
		Gdx.graphics.setWindowedMode(width, height);
        viewport.update(width, height, true);
		for (Screen screen : screenStack) {
			screen.resize(width, height);
		}
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        clearScreens();
    }

    public BitmapFont loadFont(String fontPath, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize; // Schriftgröße setzen
        BitmapFont customFont = generator.generateFont(parameter);
        generator.dispose(); // Generator freigeben, um Speicherlecks zu vermeiden
        return customFont;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void exit() {
        Gdx.app.exit();
    }

    public GameSystemManager getGameSystemManager() {
        return gameSystemManager;
    }

    public void setGameSystemManager(GameSystemManager gameSystemManager) {
        this.gameSystemManager = gameSystemManager;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    /**
     * Starting game method
     * @param playerName
     * @param selectedSlot
     */
    public void onPlayerNameConfirmed(String playerName, int selectedSlot) {

        System.out.println("Player name confirmed: " + playerName);

        // Neues Spiel starten
        Player player = new Player(playerName);

        pushScreen(new LoadingScreen(this, player, selectedSlot));
    }


}
