package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.NPC;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class TradingScreen extends ScreenAdapter {
    private final Engine engine;
    private final Player player;
    private final NPC npc;
    private boolean isBuying = true;  // true = Kaufen, false = Verkaufen
    private int selectedIndex = 0;
    private SpriteBatch batch;
    private BitmapFont font;

    public TradingScreen(Engine engine, NPC npc, Player player) {
        this.engine = engine;
        this.npc = npc;
        this.player = player;
        this.batch = engine.getBatch();
        this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 27);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        drawTradeMenu();
        batch.end();

        handleInput();
    }

    private void drawTradeMenu() {
        List<Item> items = isBuying ? npc.getInventory() : player.getInventory();
        font.draw(batch, isBuying ? "NPC Shop" : "Your Inventory", 50, 1020);

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String text = (selectedIndex == i ? "> " : "  ") + item.getName();
            if (isBuying) {
                text += " (" + item.getBuyPrice() + " Gold)";
            } else {
                text += " (" + item.getSellPrice() + " Gold)";
            }
            font.draw(batch, text, 50, 950 - (i * 30));
        }

        font.draw(batch, "[SPACE] " + (isBuying ? "Buy" : "Sell"), 50, 100);
        font.draw(batch, "[TAB] Switch Mode", 50, 70);
        font.draw(batch, "[ESC] Exit", 50, 40);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = Math.max(0, selectedIndex - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = Math.min((isBuying ? npc.getInventory() : player.getInventory()).size() - 1, selectedIndex + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            isBuying = !isBuying; // Umschalten zwischen Kaufen & Verkaufen
            selectedIndex = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            initiateTrade();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            engine.popScreen();
        }
    }

    private void initiateTrade() {
        Item selectedItem = isBuying ? npc.getInventory().get(selectedIndex) : player.getInventory().get(selectedIndex);
        int price = isBuying ? selectedItem.getBuyPrice() : selectedItem.getSellPrice();

        engine.pushScreen(new ConfirmPopup(engine, selectedItem, price, isBuying, npc, player, this));
    }
}
