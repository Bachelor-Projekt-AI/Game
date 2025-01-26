package org.bachelorprojekt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;

public class ChapterOne extends Chapter {
    public ChapterOne(Engine engine) {
        super("Kapitel 1", "Der Beginn einer Reise", engine);
    }

    @Override
    public void start(Player player) {
        System.out.println("Kapitel 1 gestartet");
    }

    @Override
    public void render() {

    }

    @Override
    protected void handleInput() {
        // with e open inventory, with m open map, with esc open pause menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("Inventory opened.");
            // Hier kannst du eine Aktion ausführen, wenn das Inventar geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println("Map opened.");
            // Hier kannst du eine Aktion ausführen, wenn die Karte geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Pause menu opened.");
            // Hier kannst du eine Aktion ausführen, wenn das Pause-Menü geöffnet wird
        }
    }
}
