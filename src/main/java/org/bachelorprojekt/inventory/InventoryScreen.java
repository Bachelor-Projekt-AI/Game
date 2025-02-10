package org.bachelorprojekt.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.lwjgl.opengl.GL20;

public class InventoryScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Engine engine;
    private Player player;

    public InventoryScreen(Engine engine) {
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.player = engine.getGameSystemManager().getPlayer();
        this.engine = engine;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Hintergrund zeichnen
        font.draw(batch, "+-----------------------------+", 50, 500);
        font.draw(batch, "|         INVENTORY          |", 50, 480);
        font.draw(batch, "+-----------------------------+", 50, 460);

        // Zeige die Items im Inventar
        int yPosition = 440;
        for (Item item : player.getInventory()) {
            font.draw(batch, "- " + item.getName(), 60, yPosition);
            yPosition -= 20; // Nächste Zeile
        }

        // Schließen-Anweisung anzeigen
        font.draw(batch, "+-----------------------------+", 50, yPosition - 20);
        font.draw(batch, "Press 'E' to close the inventory.", 50, yPosition - 40);

        batch.end();

        handleInput();
    }

    protected void handleInput() {
        // Inventar schließen
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            engine.getGameSystemManager().setInventoryOpen(false);
            System.out.println("Inventory closed.");
            // Hier kannst du eine Aktion ausführen, wenn das Inventar geschlossen wird
        }
    }
}
