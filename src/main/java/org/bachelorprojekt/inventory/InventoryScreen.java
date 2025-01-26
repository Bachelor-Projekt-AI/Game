package org.bachelorprojekt.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.Scene;

public class InventoryScreen extends Scene {
    private SpriteBatch batch;
    private BitmapFont font;
    private Player player;

    public InventoryScreen(Engine engine, Player player) {
        super(engine);
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.player = player;
    }

    @Override
    public void render() {
        // Hintergrund zeichnen
        font.draw(batch, "+-----------------------------+", 50, 500);
        font.draw(batch, "|         INVENTORY          |", 50, 480);
        font.draw(batch, "+-----------------------------+", 50, 460);

        // Zeige die Items im Inventar
        int yPosition = 440;
        for (String item : player.getInventory()) {
            font.draw(batch, "- " + item, 60, yPosition);
            yPosition -= 20; // Nächste Zeile
        }

        // Schließen-Anweisung anzeigen
        font.draw(batch, "+-----------------------------+", 50, yPosition - 20);
        font.draw(batch, "Press 'E' to close the inventory.", 50, yPosition - 40);
    }

    @Override
    protected void handleInput() {
        // Inventar schließen
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("Inventory closed.");
            // Hier kannst du eine Aktion ausführen, wenn das Inventar geschlossen wird
        }
    }
}
