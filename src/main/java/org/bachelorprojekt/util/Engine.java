package org.bachelorprojekt.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.ui.Menu;
import org.bachelorprojekt.util.json.JsonLoader;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Map;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Engine extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;

    private GameStateManager gameStateManager;
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

    public void clearScreens() {
        while (!screenStack.isEmpty()) {
            Screen screen = screenStack.pop();
            screen.dispose();
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = loadFont("fonts/PressStart2P-vaV7.ttf", 24);
        viewport = new FitViewport(1920, 1080);
        viewport.apply();

        Player player = new Player("Hero");
        player.addToInventory("Sword");
        player.addToInventory("Shield");
        player.addToInventory("Health Potion");

        textRenderer = new TextRenderer(this);

        pushScreen(new Menu(this, new String[]{"Play", "Options", "Exit"}));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public GameStateManager loadGameData(Engine engine, Player player, String chaptersFile, String locationsFile, String questsFile, String mapsFile) throws Exception {

        List<Chapter> chapters = JsonLoader.loadChapters(chaptersFile);
        List<Location> locations = JsonLoader.loadLocations(locationsFile);
        List<Quest> quests = JsonLoader.loadQuests(questsFile);
        List<Map> maps = JsonLoader.loadMaps(mapsFile);

        return new GameStateManager(engine, player, chapters, locations, quests, maps);
    }

    public void onPlayerNameConfirmed(String playerName, int selectedSlot) {
        System.out.println("Player name confirmed: " + playerName);

        // Neues Spiel starten
        Player player = new Player(playerName);

        try {
            // Erstelle GameStateManager mit neuen Daten
            GameStateManager gameStateManager = loadGameData(this, player,
                    "json/chapters.json",
                    "json/locations.json",
                    "json/quests.json",
                    "json/maps.json");

            setGameStateManager(gameStateManager);
            System.out.println(gameStateManager.getChapterById(1));

            // Initiales Kapitel starten
            Chapter firstChapter = gameStateManager.getChapterById(1);
            pushScreen(new ChapterScreen(this, firstChapter));

        } catch (Exception e) {
            System.err.println("Failed to load game data: " + e.getMessage());
        }
    }


}
