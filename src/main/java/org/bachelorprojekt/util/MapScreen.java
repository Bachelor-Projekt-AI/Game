package org.bachelorprojekt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.ui.ConfirmSelection;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Map;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class MapScreen extends ScreenAdapter {
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final Engine engine;

	private final ChapterScreen chapter;

    private final Map map;
    private final String placeholder;
    private final Location playerLocation;
    private final List<Location> locations;
    private List<Location> selectableLocations; // Orte, an denen der Spieler nicht ist
    private int selectedLocationIndex = 0;      // Index des aktuell ausgewählten Ortes

    private final float startX;
    private final float startY;

	private final float optionsX;
	private final float optionsY;

    public MapScreen(Engine engine, Map map, float startY, ChapterScreen parent) {
        this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 22);
        this.batch = engine.getBatch();
        this.engine = engine;
        this.map = map;
        System.out.println(map.getLocationIds().get(0));
        this.playerLocation = engine.getGameStateManager().getPlayer().getLocation();
        this.locations = engine.getGameStateManager().getLocationsForChapter(map.getLocationIds());
        this.selectableLocations = locations.stream()
                .filter(location -> !location.equals(playerLocation)) // Nur Orte außer Spielerstandort
                .toList();

        this.placeholder = map.getPlaceholder();

        System.out.println("Player location: " + playerLocation.getName());

		int longest = map.getLayout().stream().map(this::getStringLengthPx).max(Integer::compare).get();
		this.startX = (1920 - longest) / 2;
        this.startY = startY;

		this.chapter = parent;

		float optionsWidth = selectableLocations.stream().map(loc -> getStringLengthPx(loc.getName())).reduce(Integer::sum).get()
			+ (selectableLocations.size() - 1) * 75;
		this.optionsX = (1920 - optionsWidth) / 2;
        this.optionsY = startY - map.getLayout().size() * 25 - 50; // Position unterhalb der Karte
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(chapter != null) {
			chapter.draw();
		}

        batch.begin();
        // Zeichne die Karte Zeile für Zeile
        for (int i = 0; i < map.getLayout().size(); i++) {
            String line = map.getLayout().get(i);
            String updatedLine = updateLine(line, i);
            font.draw(batch, updatedLine, startX, startY - i * 25);
        }

        // Zeichne die auswählbaren Orte unter der Karte
		float currentX = optionsX;
        for (int i = 0; i < selectableLocations.size(); i++) {
            Location location = selectableLocations.get(i);
            String locationText = location.getName();

            // Markiere die aktuell ausgewählte Location gelb
            if (i == selectedLocationIndex) {
                font.setColor(1, 1, 0, 1); // Gelb
				locationText = "> " + locationText;
            }
			font.draw(batch, locationText, currentX, optionsY);
			font.setColor(1, 1, 1, 1); // Zurücksetzen auf Weiß
			currentX += 75 + getStringLengthPx(location.getName());
        }
        batch.end();

        handleInput();
    }

	private int getStringLengthPx(String text) {
			GlyphLayout layout = new GlyphLayout();
			layout.setText(font, text);
			return (int) layout.width;
	}

    private void handleInput() {
        // Navigation zwischen den auswählbaren Orten
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            selectedLocationIndex = (selectedLocationIndex + 1) % selectableLocations.size();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            selectedLocationIndex = (selectedLocationIndex - 1 + selectableLocations.size()) % selectableLocations.size();
        }

        // Bestätigung der Auswahl
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Location selectedLocation = selectableLocations.get(selectedLocationIndex);
            System.out.println("Ausgewählter Ort: " + selectedLocation.getName());

            engine.pushScreen(new ConfirmSelection(
                    engine,
                    "Möchtest du zum Ort '" + selectedLocation.getName() + "' reisen?",
                    () -> {
                        // Aktion für "Ja" -> Spieler wird zum ausgewählten Ort bewegt

                        engine.getGameStateManager().setPlayerLocation(selectedLocation);
                        engine.popScreen();
                    },
                    () -> {
                        // Aktion für "Nein" -> Zurück zur Karte
                    },
					chapter
            ));
        }

        // Zurück zur Karte (ESC oder M drücken)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            engine.getGameStateManager().closeMap();
        }
    }


    private String updateLine(String line, int lineIndex) {
        // Prüfe, ob die nächste Zeile existiert
        if (lineIndex + 1 < map.getLayout().size()) {
            String nextLine = map.getLayout().get(lineIndex + 1);

            // Prüfe, ob eine Location in der nächsten Zeile liegt
            for (Location location : locations) {
                if (nextLine.contains(location.getName())) {
                    // Spielerstandort markieren
                    if (location.equals(playerLocation)) {
                        return line.replace(placeholder, "*"); // Spielerstandort mit "*" markieren
                    } else {
                        return line.replace(placeholder, "o"); // Andere Orte mit "o" markieren
                    }
                }
            }
        }

        return line; // Keine Änderungen, wenn keine Location in dieser Zeile liegt
    }





}
