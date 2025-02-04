package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;

public class Menu extends Scene {

    private final String[] menuOptions;
    private int selectedOption;
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final float startY;
    private final Viewport viewport;

    public Menu(Engine engine, String[] menuOptions) {
        super(engine);
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.startY = 280;
        this.selectedOption = 0;
        this.menuOptions = menuOptions;
        this.viewport = new FitViewport(1920, 1080);
        this.viewport.apply();

        addRenderableElement(new RenderableElement(4, 1, "Lights of Akahzan")); // Titel
        for (int i = 0; i < menuOptions.length; i++) {
            final int index = i; // Benötigt für Lambda
            addRenderableElement(new RenderableElement(4, 2 + i, menuOptions[i]) {
                @Override
                public void render(SpriteBatch batch, BitmapFont font) {
                    if (index == selectedOption) {
                        font.setColor(1, 1, 0, 1); // Gelb
                        super.render(batch, font);
                        font.setColor(1, 1, 1, 1); // Weiß zurücksetzen
                    } else {
                        super.render(batch, font);
                    }
                }
            });
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        engine.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        super.render(delta);

        handleInput();
    }

    @Override
    public void handleInput() {
        // Nach unten navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        }

        // Nach oben navigieren
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        }

        // Auswahl bestätigen (falls benötigt, z. B. ENTER-Taste)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedOption == 0) {
                engine.pushScreen(new GameSelectMenu(engine));
                // engine.pushScreen(new ChapterOne(engine));
                // engine.setScene(new GameScreen(engine));
            } else if (selectedOption == 1) {
                // engine.setScene(new OptionsScreen(engine));
                engine.pushScreen(new ResolutionSelectMenu(engine));
            } else if (selectedOption == 2) {
                engine.exit();
            }
            System.out.println("Ausgewählte Option: " + menuOptions[selectedOption]);
            // Hier kannst du eine Aktion basierend auf der Auswahl ausführen
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("ChapterOneScreen resources disposed.");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
