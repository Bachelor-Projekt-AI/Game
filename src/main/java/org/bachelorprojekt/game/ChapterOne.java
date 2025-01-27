package org.bachelorprojekt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.inventory.InventoryScreen;
import org.bachelorprojekt.ui.PauseMenu;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.JsonLoader;
import org.bachelorprojekt.util.MapRenderer;

public class ChapterOne extends Chapter {

    private final String[] mapData;
    private final MapRenderer mapRenderer;
    private final String description;

    public ChapterOne(Engine engine, boolean newGame) {
        super("Kapitel 1", "Der Beginn einer Reise", engine);
        Story.Chapter currentChapter = engine.getGameStateManager().getCurrentChapter();
        this.mapData = currentChapter.getMap().get(0).getMapLines().toArray(new String[0]);
        this.description = currentChapter.getDescription();

        this.mapRenderer = new MapRenderer(engine, mapData, 50, 500);
    }

    @Override
    public void start(Player player) {
        System.out.println("Kapitel 1 gestartet");
    }

    @Override
    public void render(float delta) {

        engine.getBatch().begin();

        engine.getFont().draw(engine.getBatch(), "You are in the village of Eld.", 50, 400);
        engine.getFont().draw(engine.getBatch(), "Nearby locations:", 50, 370);
        engine.getFont().draw(engine.getBatch(), "- The blacksmith (west)", 50, 340);
        engine.getFont().draw(engine.getBatch(), "- The healer's hut (north)", 50, 310);
        engine.getFont().draw(engine.getBatch(), "- The innkeeper (south)", 50, 280);
        engine.getFont().draw(engine.getBatch(), "Press 'M' to view the map.", 50, 250);

        engine.getBatch().end();

        handleInput();
    }

    protected void handleInput() {
        // with e open inventory, with m open map, with esc open pause menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            this.engine.getGameStateManager().setInventoryOpen(true);
            //this.engine.pushScreen(new InventoryScreen(engine, engine.getGameStateManager().getPlayer()));
            System.out.println("Inventory opened.");
            // Hier kannst du eine Aktion ausführen, wenn das Inventar geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println("Map opened.");
            this.engine.getScreenStack().push(this.mapRenderer);
            // Hier kannst du eine Aktion ausführen, wenn die Karte geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Pause menu opened.");
            engine.getScreenStack().push(new PauseMenu(engine));
            // Hier kannst du eine Aktion ausführen, wenn das Pause-Menü geöffnet wird
        }
    }
}
