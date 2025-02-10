package org.bachelorprojekt.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.ui.LoadingScreen;
import org.bachelorprojekt.ui.Menu;

import java.util.Stack;

public class Engine extends Game {

    private SpriteBatch batch;
    private BitmapFont font;

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
        textRenderer = new TextRenderer(this);

        Gdx.graphics.setForegroundFPS(60);

        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    @Override
    public void resize(int width, int height) {
		Gdx.graphics.setWindowedMode(width, height);
    }

    @Override
    public void render() {
        super.render();
        if (gameSystemManager != null) {
            gameSystemManager.getChapterSystem().update();
        }
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
        parameter.mono = true; // Monospace-Schriftart verwenden
        BitmapFont customFont = generator.generateFont(parameter);
        setAllFixedWidth(customFont); // Alle Zeichen auf gleiche Breite setzen
        generator.dispose(); // Generator freigeben, um Speicherlecks zu vermeiden
        return customFont;
    }

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

    public static BitmapFont getSpecialFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JetBrainsMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[{]}|;:'\",<.>/?\\`~■"; // Hier alle benötigten Zeichen einfügen
        parameter.size = 26;
        BitmapFont font = generator.generateFont(parameter);
        setAllFixedWidth(font);
        generator.dispose();
        return font;
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
