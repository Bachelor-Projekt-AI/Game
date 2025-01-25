package org.bachelorprojekt.util;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static java.awt.Font.createFont;

public class Engine extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont font;
    private GameStateManager gameStateManager;
    private TextRenderer textRenderer;
    private MapRenderer mapRenderer;
    private Scene scene;

    public Engine() {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = loadFont("fonts/PressStart2P-vaV7.ttf", 24);

        gameStateManager = new GameStateManager();
        textRenderer = new TextRenderer(font, batch);
        mapRenderer = new MapRenderer(font, batch);

        setScene(new Menu(batch, font, new String[]{"Play", "Options", "Exit"}));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (scene != null) {
            scene.render();
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    private BitmapFont loadFont(String fontPath, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize; // Schriftgröße setzen
        BitmapFont customFont = generator.generateFont(parameter);
        generator.dispose(); // Generator freigeben, um Speicherlecks zu vermeiden
        return customFont;
    }
}
