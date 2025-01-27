package org.bachelorprojekt.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.Chapter;
import org.bachelorprojekt.game.ChapterOne;
import org.bachelorprojekt.ui.Menu;

import java.util.Stack;

public class Engine extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private GameStateManager gameStateManager;
    private TextRenderer textRenderer;
    private MapRenderer mapRenderer;
    private final Stack<Screen> screenStack;

    public Engine() {
        this.screenStack = new Stack<>();
    }

    public void pushScreen(Screen screen) {
        this.screenStack.push(screen);
    }

    public void popScreen() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    public void clearScreens() {
        screenStack.clear();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = loadFont("fonts/PressStart2P-vaV7.ttf", 24);


        Player player = new Player("Hero");
        player.addToInventory("Sword");
        player.addToInventory("Shield");
        player.addToInventory("Health Potion");

        //Chapter c = new ChapterOne(this);
        textRenderer = new TextRenderer(this);
        //mapRenderer = new MapRenderer(this);

        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
        //pushScene(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!screenStack.isEmpty()) {
            screenStack.peek().render(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        //getScreen().dispose();
    }

    private BitmapFont loadFont(String fontPath, int fontSize) {
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

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public Stack<Screen> getScreenStack() {
        return screenStack;
    }
}
