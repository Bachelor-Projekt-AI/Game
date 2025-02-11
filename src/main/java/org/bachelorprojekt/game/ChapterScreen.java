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
    private final BitmapFont font;

    public ChapterScreen(Engine engine, Chapter chapter, GameSystemManager gm) {
        this.engine = engine;
        this.chapter = chapter;
        this.player = gm.getPlayer();
        font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 27);

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
        font.draw(engine.getBatch(), "Chapter: " + chapter.getTitle(), 50, 1020);
		List<String> description = splitAtSpace(chapter.getDescription(), 78); // 1 char is 16 wide. Quests start at 1352, giving us 1252/16 = 78.25 chars
		int descriptionLine = 0;
		for (String line : description) {
			font.draw(engine.getBatch(), line, 50, 975 - descriptionLine++ * 30);
		}

        // Dynamisch geladene Locations anzeigen
		int locationsOffset = 960 - descriptionLine * 30;
        font.draw(engine.getBatch(), "Nearby locations:", 50, locationsOffset);
        int locationLine = 0;
        for (Location location : locations) {
			String fullDescription = location.getName() + ": " + location.getDescription();
			List<String> lines = splitAtSpace(fullDescription, 78);
			font.draw(engine.getBatch(), "- " + lines.removeFirst(), 50, locationsOffset - (++locationLine * 30));
			for (String line : lines) {
				font.draw(engine.getBatch(), line, 82, locationsOffset - (++locationLine * 30)); // move 2 chars = 32px to the right
			}
        }

        // **Dynamisch die aktiven Quests abrufen!**
        List<QuestInstance> activeQuests = engine.getGameSystemManager().getQuestSystem().getActiveQuests();
		int questLine = 0;
        font.draw(engine.getBatch(), "Active Quests:", 1352, 1020);
        for (QuestInstance quest : activeQuests) {
			String title = quest.getQuestData().getTitle();
			List<String> lines = splitAtSpace(title, 31); // Quests start at 1352 and end at 1870 (considering 50 padding), giving us 528/16 = 33 chars per line, remove two for "- "
            font.draw(engine.getBatch(), "- " + lines.removeFirst(), 1352, 990 - questLine++ * 30);
			for (String line : lines) {
				font.draw(engine.getBatch(), line, 1384, 990 - questLine++ * 30);
			}
        }

        engine.getBatch().end();
    }

	private List<String> splitAtSpace(String input, int maxLen) {
		List<String> list = new ArrayList<String>();
		if (input.length() > maxLen) {
			int i = maxLen;
			while (input.charAt(i--) != ' ') ;
			list.add(input.substring(0, i+1));
			list.addAll(splitAtSpace(input.substring(i+2), maxLen));
		} else {
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
