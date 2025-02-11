package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.bachelorprojekt.game.events.EventDispatcher;
import org.bachelorprojekt.game.events.LocationReachEvent;
import org.bachelorprojekt.game.events.NPCInteractionEvent;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.Arrays;

import org.bachelorprojekt.game.ChapterScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.bachelorprojekt.util.json.jackson.NPC;
import org.lwjgl.opengl.GL20;

import java.util.Arrays;

public class ContextMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String[] options = {
            "Suche nach Gegenständen",
            "Spreche mit Charakteren",
            "Untersuche ein bestimmtes Objekt"
    };

    private int selectedOption = 0; // Hauptmenü-Option
    private int selectedSubOption = 0; // Sub-Menü-Option
    private boolean isSubMenuOpen = false;

    private final float startY = 300;
    private final float menuX = 50; // X-Position des Hauptmenüs
    private final float subMenuX; // X-Position des Sub-Menüs
    private final BitmapFont contextMenuFont;

	private final ChapterScreen chapter;

    public ContextMenu(Engine engine, ChapterScreen chapter) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.contextMenuFont = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 27);
		this.chapter = chapter;

		subMenuX = 95 + Arrays.stream(options).map(option -> {
			GlyphLayout layout = new GlyphLayout();
			layout.setText(contextMenuFont, "> " + option);
			return (int) layout.width;
		}).max(Integer::compare).get();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (chapter != null) {
			chapter.draw();
		}

        engine.getBatch().begin();

        // Zeichne das Hauptmenü
        drawMenu();

        // Zeichne das Sub-Menü, falls es geöffnet ist
        if (isSubMenuOpen) {
            drawSubMenu();
        }

        engine.getBatch().end();

        handleInput();
    }

    private void drawMenu() {
        contextMenuFont.draw(engine.getBatch(), "Was möchtest du tun?", menuX, startY);
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                contextMenuFont.draw(engine.getBatch(), "> " + options[i], menuX, startY - 50 - (i * 30));
            } else {
                contextMenuFont.draw(engine.getBatch(), options[i], menuX, startY - 50 - (i * 30));
            }
        }
    }

    private void drawSubMenu() {
        String[] currentSubOptions = getCurrentSubOptions();
        contextMenuFont.draw(engine.getBatch(), "Optionen:", subMenuX, startY);
        for (int i = 0; i < currentSubOptions.length; i++) {
            if (i == selectedSubOption) {
                contextMenuFont.setColor(1, 1, 0, 1); // Gelb
                contextMenuFont.draw(engine.getBatch(), "> " + currentSubOptions[i], subMenuX, startY - 50 - (i * 30));
                contextMenuFont.setColor(1, 1, 1, 1); // Weiß
            } else {
                contextMenuFont.draw(engine.getBatch(), currentSubOptions[i], subMenuX, startY - 50 - (i * 30));
            }
        }
    }

    private void handleInput() {
        if (!isSubMenuOpen) {
            // Navigation im Hauptmenü
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedOption = (selectedOption + 1) % options.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                selectedOption = (selectedOption - 1 + options.length) % options.length;
            }

            // Öffnen des Sub-Menüs
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                isSubMenuOpen = true;
                selectedSubOption = 0; // Sub-Menü auf Anfangsoption setzen
            }

            // Abbrechen des Menüs
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                engine.popScreen();
            }
        } else {
            // Hole aktuell verfügbare Sub-Optionen
            String[] currentSubOptions = getCurrentSubOptions();

            // Navigation im Sub-Menü
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedSubOption = (selectedSubOption + 1) % currentSubOptions.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                selectedSubOption = (selectedSubOption - 1 + currentSubOptions.length) % currentSubOptions.length;
            }

            // Auswahl im Sub-Menü bestätigen
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                engine.popScreen();
                executeAction();
                 // Menü schließen
            }

            // Sub-Menü schließen
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isSubMenuOpen = false;
            }

			if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
				engine.popScreen();
			}
        }
    }


    private String[] getCurrentSubOptions() {
        switch (selectedOption) {
            case 0:
                return generateSubOptionsForObjects();
            case 1:
                return generateSubOptionsForNPCs();
            case 2:
                return generateSubOptionsForExaminableObjects();
            default:
                return new String[]{};
        }
    }

    private String[] generateSubOptionsForNPCs() {
        // Beispiel: NPCs in der aktuellen Umgebung
        return engine.getGameSystemManager().getPlayer().getLocation().getNpcs()
                .stream().map(NPC::getName).toArray(String[]::new);
    }

    // Platzhalter-Methoden für die anderen Fälle (kannst du bei Bedarf implementieren)
    private String[] generateSubOptionsForObjects() {
        // Beispiel: Hole die Namen der Objekte in der aktuellen Location
        // return engine.getGameStateManager().getCurrentLocationObjects()
        //         .stream().map(obj -> obj.getName()).toArray(String[]::new);
        return new String[]{"Baum", "Felsen", "Kiste"};
    }

    private String[] generateSubOptionsForExaminableObjects() {
        // Beispiel: Unterscheidbare Objekte zum Untersuchen
        // return engine.getGameStateManager().getExaminableObjects()
        //         .stream().map(obj -> obj.getName()).toArray(String[]::new);
        return new String[]{"Statue", "Buch", "Schriftrolle"};
    }


    private void executeAction() {
        switch (selectedOption) {
            case 0 -> { // Gegenstände suchen (Beispiel)
                String selectedObject = generateSubOptionsForObjects()[selectedSubOption];
                System.out.println("Du suchst nach: " + selectedObject);
                // Hier könnten wir ein `ItemCollectEvent` auslösen, wenn das sinnvoll wäre
            }
            case 1 -> { // Sprechen mit Charakteren
                String selectedNPCName = generateSubOptionsForNPCs()[selectedSubOption];
                NPC selectedNPC = engine.getGameSystemManager().getPlayer().getLocation().getNpcs()
                        .stream()
                        .filter(npc -> npc.getName().equals(selectedNPCName))
                        .findFirst().orElse(null);

                if (selectedNPC != null) {
                    System.out.println("Du sprichst mit: " + selectedNPCName);
                    // get random dialogue from npc
                    String dialogue = selectedNPC.getDialogues().get((int) (Math.random() * selectedNPC.getDialogues().size()));
                    engine.sendNotification(selectedNPCName + ": " + dialogue);
                    // **Neues NPC-Interaktions-Event auslösen!**
                    EventDispatcher.dispatchEvent(new NPCInteractionEvent(selectedNPC.getId()));
                }
            }
            case 2 -> { // Ort untersuchen oder erreichen
                String selectedLocation = generateSubOptionsForExaminableObjects()[selectedSubOption];
                System.out.println("Du untersuchst: " + selectedLocation);

                // **Neues Location-Event auslösen!**
                //EventDispatcher.dispatchEvent(new LocationReachEvent(engine.getGameStateManager().getCurrentLocationId()));
            }
            default -> throw new IllegalStateException("Unerwarteter Wert: " + selectedOption);
        }
    }

}
