package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import org.bachelorprojekt.game.ChapterOne;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.lwjgl.opengl.GL20;

public class Menu extends ScreenAdapter {

    private final String[] menuOptions;
    private int selectedOption;
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final float startY;

    public Menu(Engine engine, String[] menuOptions) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.startY = 280;
        this.selectedOption = 0;
        this.menuOptions = menuOptions;
    }

    @Override
    public void render(float delta) {

        engine.getBatch().begin();

        textRenderer.drawCenteredText("Lights of Akahzan", 500);
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                textRenderer.drawCenteredText("> " + menuOptions[i], startY - i * 30);
            } else {
                textRenderer.drawCenteredText(menuOptions[i], startY - i * 30);
            }
        }

        engine.getBatch().end();

        handleInput();
    }

    private void handleInput() {
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
                engine.getScreenStack().push(new GameSelectMenu(engine));
                //engine.getScreenStack().push(new ChapterOne(engine));
                // engine.setScene(new GameScreen(engine));
            } else if (selectedOption == 1) {
                // engine.setScene(new OptionsScreen(engine));
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

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
