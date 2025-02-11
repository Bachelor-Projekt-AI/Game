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

public class ConfirmPopup extends ScreenAdapter {
    private final Engine engine;
    private final Item item;
    private final int price;
    private final boolean isBuying;
    private final NPC npc;
    private final Player player;
    private final TradingScreen parent;
    private SpriteBatch batch;
    private BitmapFont font;
    private boolean confirm = false;

    public ConfirmPopup(Engine engine, Item item, int price, boolean isBuying, NPC npc, Player player, TradingScreen parent) {
        this.engine = engine;
        this.item = item;
        this.price = price;
        this.isBuying = isBuying;
        this.npc = npc;
        this.player = player;
        this.parent = parent;
        this.batch = engine.getBatch();
        this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 27);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Confirm " + (isBuying ? "purchase" : "sale") + " of " + item.getName() + " for " + price + " Gold?", 100, 500);
        font.draw(batch, confirm ? "> Yes" : "  Yes", 100, 450);
        font.draw(batch, confirm ? "  No" : "> No", 100, 420);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            confirm = !confirm;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (confirm) {
                if (isBuying && player.getGold() >= price) {
                    player.addToInventory(item);
                    player.subtractGold(price);
                } else if (!isBuying) {
                    player.removeFromInventory(item);
                    player.addGold(price);
                }
            }
            engine.popScreen();
        }
    }
}
