package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.TextRenderer;
import org.bachelorprojekt.util.json.jackson.NPC;
import org.lwjgl.opengl.GL20;

public class ContextMenu extends ScreenAdapter {
    private final Engine engine;
    private final TextRenderer textRenderer;
    private final String[] options = {
            "Suche nach Gegenständen",
            "Spreche mit Charakteren",
            "Untersuche ein bestimmtes Objekt"
    };
    private final String[][] subOptions = {
            {"Baum", "Felsen", "Kiste"}, // Sub-Optionen für "Suche nach Gegenständen"
            {"Healer", "Blacksmith", "Innkeeper"}, // Sub-Optionen für "Spreche mit Charakteren"
            {"Statue", "Buch", "Schriftrolle"} // Sub-Optionen für "Untersuche ein bestimmtes Objekt"
    };

    private int selectedOption = 0; // Hauptmenü-Option
    private int selectedSubOption = 0; // Sub-Menü-Option
    private boolean isSubMenuOpen = false;

    private final float startY = 300;
    private final float menuX = 50; // X-Position des Hauptmenüs
    private final float subMenuX = 350; // X-Position des Sub-Menüs
    private final BitmapFont contextMenuFont;

    public ContextMenu(Engine engine) {
        this.engine = engine;
        this.textRenderer = engine.getTextRenderer();
        this.contextMenuFont = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 16);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        String[] currentSubOptions = subOptions[selectedOption];
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
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                engine.popScreen();
            }
        } else {
            // Navigation im Sub-Menü
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedSubOption = (selectedSubOption + 1) % subOptions[selectedOption].length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                selectedSubOption = (selectedSubOption - 1 + subOptions[selectedOption].length) % subOptions[selectedOption].length;
            }

            // Auswahl im Sub-Menü bestätigen
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                String selectedAction = subOptions[selectedOption][selectedSubOption];
                System.out.println("Ausgewählte Aktion: " + selectedAction);
                engine.popScreen(); // Zurück zum Spiel oder vorherigen Menü
            }

            // Sub-Menü schließen
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isSubMenuOpen = false;
            }
        }
    }

    private void executeAction() {
        switch (selectedOption) {
            case 0 -> {
                // Suche nach Gegenständen
                //String selectedObject = generateSubOptionsForObjects()[selectedSubOption];
                System.out.println("Du suchst nach: ");
                //engine.getGameStateManager().searchForObject(selectedObject);
            }
            case 1 -> {
                // Sprechen mit Charakteren
                String selectedNPC = generateSubOptionsForNPCs()[selectedSubOption];
                System.out.println("Du sprichst mit: " + selectedNPC);
                engine.getGameStateManager().talkToNPC(selectedNPC);
            }
            case 2 -> {
                // Untersuchen eines Objekts
                //String selectedObject = generateSubOptionsForExaminableObjects()[selectedSubOption];
                System.out.println("Du untersuchst: ");
                //engine.getGameStateManager().examineObject(selectedObject);
            }
            default -> throw new IllegalStateException("Unexpected value: " + selectedOption);
        }
        engine.popScreen();
    }

/*    private String[] generateSubOptionsForObjects() {
        // Beispiel: Gegenstände in der aktuellen Umgebung
        return engine.getGameStateManager().getCurrentLocationObjects()
                .stream().map(Object::getName).toArray(String[]::new);
    }*/

    private String[] generateSubOptionsForNPCs() {
        // Beispiel: NPCs in der aktuellen Umgebung
        return engine.getGameStateManager().getNPCsInCurrentLocation()
                .stream().map(NPC::getName).toArray(String[]::new);
    }

    /*private String[] generateSubOptionsForExaminableObjects() {
        // Beispiel: Untersuchbare Objekte in der Umgebung
        return engine.getGameStateManager().getExaminableObjects()
                .stream().map(Object::getName).toArray(String[]::new);
    }*/


}
