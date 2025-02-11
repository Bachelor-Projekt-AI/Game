package org.bachelorprojekt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.combat.CombatSystem;
import org.bachelorprojekt.quest.QuestInstance;
import org.bachelorprojekt.ui.ContextMenu;
import org.bachelorprojekt.ui.HelpScreen;
import org.bachelorprojekt.ui.PauseMenu;
import org.bachelorprojekt.ui.QuestScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.GameSystemManager;
import org.bachelorprojekt.util.Keybind;
import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Enemy;
import org.bachelorprojekt.util.json.jackson.Location;
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
        // Sicherstellen, dass das GameSystemManager existiert
        if (engine.getGameSystemManager() != null && engine.getGameSystemManager().getQuestSystem() != null) {
            List<QuestInstance> activeQuests = engine.getGameSystemManager().getQuestSystem().getActiveQuests();
            int questLine = 0;
            font.draw(engine.getBatch(), "Active Quests:", 1352, 1020);
            for (QuestInstance quest : activeQuests) {
                String title = quest.getQuestData().getTitle();
                List<String> lines = splitAtSpace(title, 31);
                font.draw(engine.getBatch(), "- " + lines.removeFirst(), 1352, 990 - questLine++ * 30);
                for (String line : lines) {
                    font.draw(engine.getBatch(), line, 1384, 990 - questLine++ * 30);
                }
            }
        } else {
            String loadingText = "Loading...";
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, loadingText);
            font.draw(engine.getBatch(), "Loading...", 960 - layout.width, 540);
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
		new HelpScreen(engine, List.of(
			new Keybind(Input.Keys.C, "Open context menu", () -> {
            	this.engine.pushScreen(new ContextMenu(engine, this));
			}),
			new Keybind(Input.Keys.M, "Open map", () -> {
				System.out.println("Map opened.");
				this.engine.getGameSystemManager().openMapWithChapter(this);
			}),
			new Keybind(Input.Keys.E, "Open inventory", () -> {
		this.engine.getGameSystemManager().setInventoryOpen(true);
			System.out.println("Inventory opened.");
			}),
			new Keybind(Input.Keys.Q, "Open quest menu", () -> {
				engine.pushScreen(new QuestScreen(engine));
			}),
			new Keybind(Input.Keys.K, "Open combat screen", () -> {
				Enemy e = engine.getGameSystemManager().getEnemyManager().getEnemyById(1);
				CombatSystem combatSystem = new CombatSystem(engine, player, e);
			}),
			new Keybind(Input.Keys.ESCAPE, "Open pause menu", () -> {
				System.out.println("Pause menu opened.");
				engine.pushScreen(new PauseMenu(engine));
			})
		)).render(0);
    }
}
