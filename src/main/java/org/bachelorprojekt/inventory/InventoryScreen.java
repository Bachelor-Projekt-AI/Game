package org.bachelorprojekt.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Engine engine;
    private Player player;
    private int yPosition = 500;
    private int selectedIndex = -1;

    public InventoryScreen(Engine engine) {
        this.batch = engine.getBatch();
        //this.font = engine.getFont();
        this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 22);
        this.player = engine.getGameSystemManager().getPlayer();
        this.engine = engine;
    }

    public static int getMaxCharsPerLine(BitmapFont font) {
        float screenWidth = Gdx.graphics.getWidth(); // Fensterbreite
        GlyphLayout layout = new GlyphLayout(font, "W"); // Ein Zeichen ausmessen
        float charWidth = layout.width; // Breite eines Zeichens

        return (int) (screenWidth / charWidth) - 10; // Maximale Zeichenanzahl pro Zeile mit padding
    }

    public static int getMaxLinesOnScreen(BitmapFont font) {
        float screenHeight = Gdx.graphics.getHeight();
        float lineHeight = font.getLineHeight(); // Höhe eines einzelnen Zeichens
        return (int) (screenHeight / lineHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        String[] bp = renderInventory(player, getMaxCharsPerLine(font));
        yPosition = Gdx.graphics.getHeight() - 20;
        for (String l : bp) {
            font.draw(batch, l == null ? "" : l, 20, yPosition);
            yPosition -= 40;
        }

        batch.end();

        handleInput();
    }

    protected void handleInput() {
        // Inventar schließen
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (!player.getInventory().isEmpty()) {
                selectedIndex = Math.max(0, selectedIndex - 1); // Nach oben gehen, aber nicht unter 0
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (!player.getInventory().isEmpty()) {
                selectedIndex = Math.min(player.getInventory().size() - 1, selectedIndex + 1); // Nach unten gehen, aber nicht über das letzte Item hinaus
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            engine.getGameSystemManager().setInventoryOpen(false);
            System.out.println("Inventory closed.");
        }
    }

    public String[] renderInventory(Player player, final int INV_WITH) {
        int totalLines = getMaxLinesOnScreen(font) - 10;
        int mainHeight = (int) (totalLines * (2.0 / 3.0)) -1; // 2/3 für Backpack & Equipment
        int subHeight = (int) (totalLines * (1.0 / 3.0));  // 1/3 für Stats & Description

        final int COMPONENT_WIDTH = INV_WITH / 3;
        final List<Item> items = player.getInventory();
        selectedIndex = items.isEmpty() ? -1 : selectedIndex == -1 ? 0 : selectedIndex;
        Item selectedItem = selectedIndex >= 0 ? items.get(selectedIndex) : null;
        final String[] backpack = renderBackpack(items, COMPONENT_WIDTH, mainHeight);
        final String[] equipment = renderEquipment(items, COMPONENT_WIDTH, mainHeight);
        final String[] item = renderItem(selectedItem, COMPONENT_WIDTH, totalLines);
        final String[] description = renderDescription(selectedItem, COMPONENT_WIDTH * 2, subHeight / 2);
        final String[] stats = renderStats(COMPONENT_WIDTH * 2, subHeight / 2);


        // creation of backpack and equipment, after that description and stats
        final String[] inventory = new String[item.length];
        int counter = 0;
        for (int i = 0; i < backpack.length; i++) {
            inventory[i] = backpack[i] + equipment[i] + item[i];
            counter++;
        }

        for (int i = 0; i < stats.length; i++) {
            inventory[counter] = stats[i] + item[counter];
            counter++;
        }

        for (int i = 0; i < stats.length; i++) {
            inventory[counter] = description[i] + item[counter];
            counter++;
        }

        return inventory;
    }

    public String[] renderBackpack(List<Item> items, final int BACKPACK_WIDTH, final int HEIGHT) {

        // Header & Border
        String borderLine = "*".repeat(BACKPACK_WIDTH);
        String headerLine = "*-Backpack" + "-".repeat(BACKPACK_WIDTH - 9) + "*";

        // Template dynamisch generieren
        List<String> template = new ArrayList<>();
        template.add(headerLine);

        for (int i = 0; i < HEIGHT; i++) {
            template.add("| {slot} " + " ".repeat(BACKPACK_WIDTH - 4 - 8) + " |");
        }

        template.add(borderLine);

        // Rendered Liste erzeugen
        String[] rendered = template.toArray(new String[0]);

        // Slots mit Items oder Leerzeichen füllen
        for (int i = 0; i < HEIGHT; i++) {
            String prefix = (i == selectedIndex) ? "> " : "  ";
            String itemName = i < items.size() ? items.get(i).getName() : ""; // Falls kein Item, dann leer
            itemName = truncateString(itemName, BACKPACK_WIDTH - 4);
            String paddedItem = padEnd(itemName, BACKPACK_WIDTH - 4);
            rendered[i + 1] = "| " + prefix + paddedItem + " |"; // +1 weil Index 0 der Header ist
        }

        return rendered;
    }


    public static String[] renderEquipment(List<Item> items, final int BACKPACK_WIDTH, final int HEIGHT) {

        // Header & Border
        String borderLine = "*".repeat(BACKPACK_WIDTH);
        String headerLine = "*-Equipment" + "-".repeat(BACKPACK_WIDTH - 12) + "*";

        // Template dynamisch generieren
        List<String> template = new ArrayList<>();
        template.add(headerLine);

        for (int i = 0; i < HEIGHT; i++) {
            template.add("| {slot} " + " ".repeat(BACKPACK_WIDTH - 4 - 8) + " |");
        }

        template.add(borderLine);

        // Rendered Liste erzeugen
        String[] rendered = template.toArray(new String[0]);

        // Slots mit Items füllen
        for (int i = 0; i < HEIGHT; i++) {
            String itemName = i < items.size() ? items.get(i).getName() : "";
            itemName = truncateString(itemName, BACKPACK_WIDTH - 4);
            String paddedItem = padEnd(itemName, BACKPACK_WIDTH - 4);
            rendered[i + 1] = "| " + paddedItem + " |"; // +1 weil Index 0 der Header ist
        }

        return rendered;
    }

    public String[] renderItem(Item item, final int WIDTH, final int HEIGHT) {

        String borderLine = " ".repeat(WIDTH);
        String headerLine = " Item" + "".repeat(WIDTH - 7) + " ";

        boolean itemSelected = item != null;
        // Header mit Item-Namen (Falls kein Item existiert, leeres Label)
        String itemName = itemSelected ? item.getName() : "No Item Selected";
        String nameLine = " " + padEnd(itemName, WIDTH - 4) + "  "; // Breite anpassen

        // Feste Strings für Type & Rarity
        String type = itemSelected ? item.getCategory() : "";
        String rarity = itemSelected ? item.getRarity() : "";
        String typeRarityLine = " " + padEnd(type, WIDTH / 2 - 3) + "   " + padEnd(rarity, WIDTH / 2 - 3) + "  ";

        // Stats Header
        String statsHeader = " Stats  Cur ->    Equipped        ";

        // Platz für Stats (Leerzeilen)
        List<String> statsSection = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            statsSection.add(" " + " ".repeat(WIDTH - 4) + " ");
        }

        // On Equip Section
        String onEquipHeader = " On Equip" + "-".repeat(WIDTH - 14) + " ";
        List<String> onEquipSection = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            onEquipSection.add(" " + " ".repeat(WIDTH - 4) + " ");
        }

        // Actions Section
        String actionsHeader = " Actions" + " ".repeat(WIDTH - 14) + " ";
        List<String> actionsSection = new ArrayList<>();
        actionsSection.add(" SPACE to equip" + " ".repeat(WIDTH - 19) + " ");
        actionsSection.add(" Press D to drop" + " ".repeat(WIDTH - 19) + " ");
        actionsSection.add(" Press X to see stats" + " ".repeat(WIDTH - 24) + " ");

        // Restliche Leerzeilen für die Actions-Sektion (damit Höhe erreicht wird)
        int remainingLines = HEIGHT - (7 + statsSection.size() + onEquipSection.size() + actionsSection.size());
        for (int i = 0; i < remainingLines; i++) {
            actionsSection.add(" " + " ".repeat(WIDTH - 4) + " ");
        }

        // Rendering
        List<String> template = new ArrayList<>();
        template.add(headerLine);
        template.add(nameLine);
        template.add(borderLine);
        template.add(typeRarityLine);
        template.add(borderLine);
        template.add(statsHeader);
        template.addAll(statsSection);
        template.add(onEquipHeader);
        template.addAll(onEquipSection);
        template.add(actionsHeader);
        template.addAll(actionsSection);
        template.add(borderLine);

        return template.toArray(new String[0]);
    }

    public static String[] renderStats(final int WIDTH, final int HEIGHT) {
        // Header & Border
        String borderLine = "*".repeat(WIDTH);
        String headerLine = "*-Stats" + "-".repeat(Math.max(0, WIDTH - 8)) + "*"; // -7 für "*-Stats"

        // Statistiken als einzeiliger String
        String statsString = "HP: 100 | AD: 10 | AP: 15 | MR: 12 | Armor: 15 | Money: 1000";

        // Höhe anpassen (mindestens Header + Border)
        int maxLines = HEIGHT - 2; // Platz für Inhalte ohne Header & Border

        // Statistiken in mehrere Zeilen aufteilen, falls zu lang
        List<String> statLines = splitIntoLines(statsString, WIDTH - 4); // 4 Zeichen für Ränder

        // Template generieren
        List<String> template = new ArrayList<>();
        template.add(headerLine);

        for (int i = 0; i < maxLines; i++) {
            String statLine = i < statLines.size() ? statLines.get(i) : ""; // Falls kein Stat, bleibt es leer
            String paddedStat = padEnd(statLine, WIDTH - 4);
            template.add("| " + paddedStat + " |");
        }

        template.add(borderLine);

        return template.toArray(new String[0]);
    }

    private static List<String> splitIntoLines(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");

        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxWidth) { // +1 für Leerzeichen
                lines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }
            if (!currentLine.isEmpty()) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }



    public static String[] renderDescription(Item item, final int WIDTH, final int HEIGHT) {
        // Header & Border
        String borderLine = "*".repeat(WIDTH);
        String headerLine = "*-Description" + "-".repeat(Math.max(0, WIDTH - 14)) + "*"; // -7 für "*-Stats"

        // Statistiken als einzeiliger String
        String statsString = item != null ? item.getDescription() : "No item selected";

        // Höhe anpassen (mindestens Header + Border)
        int maxLines = HEIGHT - 2; // Platz für Inhalte ohne Header & Border

        // Statistiken in mehrere Zeilen aufteilen, falls zu lang
        List<String> statLines = splitIntoLines(statsString, WIDTH - 4); // 4 Zeichen für Ränder

        // Template generieren
        List<String> template = new ArrayList<>();
        template.add(headerLine);

        for (int i = 0; i < maxLines; i++) {
            String statLine = i < statLines.size() ? statLines.get(i) : ""; // Falls kein Stat, bleibt es leer
            String paddedStat = padEnd(statLine, WIDTH - 4);
            template.add("| " + paddedStat + " |");
        }

        template.add(borderLine);

        return template.toArray(new String[0]);
    }

    private static String truncateString(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    private static String padEnd(String text, int length) {
        if (text.length() > length) {
            return text.substring(0, length); // Falls zu lang, abschneiden
        }
        return text + " ".repeat(length - text.length()); // Exakt auffüllen
    }

}
