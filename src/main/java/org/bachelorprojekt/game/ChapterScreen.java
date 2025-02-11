package org.bachelorprojekt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.ui.ContextMenu;
import org.bachelorprojekt.ui.MessageScreen;
import org.bachelorprojekt.ui.PauseMenu;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Quest;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.List;

public class ChapterScreen extends ScreenAdapter {

    private final Engine engine;
    private final Chapter chapter; // Aktuelles Kapitel
    private final Player player;
    private final List<Location> locations; // Locations aus Chapter
    private final BitmapFont questFont;

    public ChapterScreen(Engine engine, Chapter chapter, GameSystemManager gm) {
        this.engine = engine;
        this.chapter = chapter;
        this.player = gm.getPlayer();
        questFont = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 27);

        System.out.println(chapter.getLocationIds().get(0));
        this.locations = chapter.getLocations();
        player.setLocation(locations.get(0));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		draw();

        handleInput();
    }

    public void draw() {
        engine.getBatch().begin();

        // Kapitel-Info anzeigen
        questFont.draw(engine.getBatch(), "Chapter: " + chapter.getTitle(), 50, 1020);
        questFont.draw(engine.getBatch(), chapter.getDescription(), 50, 975);

        // Dynamisch geladene Locations anzeigen
        questFont.draw(engine.getBatch(), "Nearby locations:", 50, 930);
        int locationOffset = 0;
        for (Location location : locations) {
            questFont.draw(engine.getBatch(),
                    "- " + location.getName() + ": " + location.getDescription(),
                    50, 900 - (locationOffset++ * 30));
        }

        // **Dynamisch die aktiven Quests abrufen!**
        List<QuestInstance> activeQuests = engine.getGameSystemManager().getQuestSystem().getActiveQuests();
		int lineNr = 0;
        questFont.draw(engine.getBatch(), "Active Quests:", 1352, 1020);
        for (QuestInstance quest : activeQuests) {
			String title = quest.getQuestData().getTitle();
			List<String> lines = splitString(title);
            questFont.draw(engine.getBatch(), "- " + lines.remove(0), 1352, 990 - lineNr++ * 30);
			for (String line : lines) {
				questFont.draw(engine.getBatch(), line, 1384, 990 - lineNr++ * 30);
			}
        }

        engine.getBatch().end();
    }

	private List<String> splitString(String input) {
		List<String> list = new ArrayList<String>();
		if (input.length() > 31) { // Quests start at 1352 and end at 1870 (considering 50 padding). 1 char is 16 wide, giving us 528/16 = 33 chars per line, remove two for "- "
			int i = 32;
			while (input.charAt(i--) != ' ') ;
			list.add(input.substring(0, i+1));
			list.addAll(splitString(input.substring(i+2)));
		}
		else {
			list.add(input);
		}
		return list;
	}

    protected void handleInput() {
        // with e open inventory, with m open map, with esc open pause menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            this.engine.getGameSystemManager().setInventoryOpen(true);
            //this.engine.pushScreen(new InventoryScreen(engine, engine.getGameStateManager().getPlayer()));
            System.out.println("Inventory opened.");
            // Hier kannst du eine Aktion ausführen, wenn das Inventar geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println("Map opened.");
            this.engine.getGameSystemManager().openMapWithChapter(this);
            // Hier kannst du eine Aktion ausführen, wenn die Karte geöffnet wird
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            this.engine.pushScreen(new ContextMenu(engine, this));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Pause menu opened.");
            engine.pushScreen(new PauseMenu(engine));
            // Hier kannst du eine Aktion ausführen, wenn das Pause-Menü geöffnet wird
        }
    }
}
