package org.bachelorprojekt.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bachelorprojekt.util.Engine.getSpecialFont;

public class InventoryScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Engine engine;
    private Player player;
    private int yPosition = 500;
    private int selectedIndex = -1;
    private float holdTimerUp = 0;
    private float holdTimerDown = 0;
    private final float initialDelay = 0.3f; // 300ms Verzögerung, bevor sich die Geschwindigkeit erhöht
    private final float repeatRate = 0.1f;   // Alle 100ms eine Bewegung, wenn Taste gehalten wird

    private int scrollOffset = 0; // Scroll-Offset für sichtbare Items
    private static final int MAX_ITEMS = 13; // Anzahl der sichtbaren Items im Backpack

    public InventoryScreen(Engine engine) {
        this.batch = engine.getBatch();
        //this.font = engine.getFont();

        //this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 22);

        this.font = getSpecialFont();


        this.player = engine.getGameSystemManager().getPlayer();
        this.engine = engine;
    }

    public static int getMaxCharsPerLine(BitmapFont font) {
        float screenWidth = 1920;
        GlyphLayout layout = new GlyphLayout(font, "W"); // Ein Zeichen ausmessen
        float charWidth = layout.width; // Breite eines Zeichens

        return (int) ((screenWidth - 40) / charWidth); // Maximale Zeichenanzahl pro Zeile mit padding
    }

    public static int getMaxLinesOnScreen(BitmapFont font) {
        float screenHeight = 1080;
        float lineHeight = font.getLineHeight(); // Höhe eines einzelnen Zeichens
        return (int) (screenHeight / lineHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        String[] bp = renderInventory(player, getMaxCharsPerLine(font));
        yPosition = 1060;
        for (String l : bp) {
            font.draw(batch, l == null ? "" : l, 20, yPosition);
            yPosition -= 40;
        }

        batch.end();

        handleInput();
    }

    protected void handleInput() {
        int totalItems = player.getInventory().size();
        float delta = Gdx.graphics.getDeltaTime();
        // Inventar schließen
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            holdTimerUp += delta;

            if (holdTimerUp == delta || holdTimerUp >= initialDelay && holdTimerUp % repeatRate < delta) {
                if (selectedIndex > 0) {
                    selectedIndex--;
                    if (selectedIndex < scrollOffset) {
                        scrollOffset = Math.max(0, scrollOffset - 1);
                    }
                }
            }
        } else {
            holdTimerUp = 0; // Timer zurücksetzen, wenn Taste losgelassen wird
        }

        // 🔽 Pfeil RUNTER gedrückt
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            holdTimerDown += delta;

            if (holdTimerDown == delta || holdTimerDown >= initialDelay && holdTimerDown % repeatRate < delta) {
                if (selectedIndex < totalItems - 1) {
                    selectedIndex++;
                    if (selectedIndex >= scrollOffset + MAX_ITEMS) {
                        scrollOffset = Math.min(totalItems - MAX_ITEMS, scrollOffset + 1);
                    }
                }
            }
        } else {
            holdTimerDown = 0; // Timer zurücksetzen, wenn Taste losgelassen wird
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            engine.getGameSystemManager().setInventoryOpen(false);
            System.out.println("Inventory closed.");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            equipOrUnequipItem();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            dropItem();
        }
    }

    private void equipOrUnequipItem() {

        if (engine.isNotificationActive()) {
            return; // Blockiere Eingaben, solange die Benachrichtigung aktiv ist
        }

        if (selectedIndex >= 0 && selectedIndex < player.getInventory().size()) {
            Item selectedItem = player.getInventory().get(selectedIndex);
            boolean equipped = false;
            if (selectedItem.isEquippable()) {
                switch (selectedItem.getItemType()) {
                    case HEAD:
                        if (player.getHead() == selectedItem) {
                            player.setHead(null);
                        } else {
                            player.setHead(selectedItem);
                            equipped = true;
                        }
                        break;
                    case BODY:
                        if (player.getBody() == selectedItem) {
                            player.setBody(null);
                        } else {
                            player.setBody(selectedItem);
                            equipped = true;
                        }
                        break;
                    case ARMS:
                        if (player.getArms() == selectedItem) {
                            player.setArms(null);
                        } else {
                            player.setArms(selectedItem);
                            equipped = true;
                        }
                        break;
                    case RING:
                        if (player.getRing() == selectedItem) {
                            player.setRing(null);
                        } else {
                            player.setRing(selectedItem);
                            equipped = true;
                        }
                        break;
                    case FEET:
                        if (player.getFeet() == selectedItem) {
                            player.setFeet(null);
                        } else {
                            player.setFeet(selectedItem);
                            equipped = true;
                        }
                        break;
                    default:
                        return;
                }
                engine.sendNotification(equipped ? "You equipped: " + selectedItem.getName() : "You unequipped: " + selectedItem.getName());
            }
        }
    }

    private void dropItem() {
        if (selectedIndex >= 0 && selectedIndex < player.getInventory().size()) {
            Item selectedItem = player.getInventory().remove(selectedIndex);
            selectedIndex--;
            engine.sendNotification("You Dropped: " + selectedItem.getName());
        }
    }


    public String[] renderInventory(Player player, final int INV_WITH) {
        int totalLines = getMaxLinesOnScreen(font) - 10;
        int mainHeight = (int) (totalLines * (2.0 / 3.0)) - 1;
        int subHeight = (int) (totalLines * (1.0 / 3.0));

        final int COMPONENT_WIDTH = INV_WITH / 3;
        final List<Item> items = player.getInventory();
        selectedIndex = items.isEmpty() ? -1 : Math.max(selectedIndex, 0);
        Item selectedItem = selectedIndex >= 0 ? items.get(selectedIndex) : null;

        final String[] backpack = renderBackpack(items, COMPONENT_WIDTH, mainHeight);
        final String[] equipment = renderEquipment(player, COMPONENT_WIDTH, mainHeight);
        final String[] item = renderItem(selectedItem, COMPONENT_WIDTH, totalLines);
        final String[] description = renderDescription(selectedItem, COMPONENT_WIDTH * 2, subHeight / 2);
        final String[] stats = renderStats(player, COMPONENT_WIDTH * 2, subHeight / 2);

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

        for (int i = 0; i < description.length; i++) {
            inventory[counter] = description[i] + item[counter];
            counter++;
        }

        return inventory;
    }

    public String[] renderBackpack(List<Item> items, final int BACKPACK_WIDTH, final int HEIGHT) {
        String borderLine = "*".repeat(BACKPACK_WIDTH);
        String headerText = "*-Backpack";
        String headerLine = headerText + "-".repeat(BACKPACK_WIDTH - headerText.length()) + "*";

        List<String> template = new ArrayList<>();
        template.add(headerLine);

        for (int i = 0; i < HEIGHT; i++) {
            template.add("| " + " ".repeat(BACKPACK_WIDTH - 4) + " |");
        }

        template.add(borderLine);

        for (int i = 0; i < MAX_ITEMS; i++) {
            int itemIndex = scrollOffset + i;
            String prefix = (itemIndex == selectedIndex) ? "> " : "  ";
            String itemName = itemIndex < items.size() ? items.get(itemIndex).getName() : "";
            String paddedItem = padEnd(itemName, BACKPACK_WIDTH - 5);
            template.set(i + 1, "| " + prefix + paddedItem + " |");
        }

        return template.toArray(new String[0]);
    }


    public static String[] renderEquipment(Player player, final int WIDTH, final int HEIGHT) {
        // Header & Border
        String borderLine = "*".repeat(WIDTH);
        String headerText = "*-Equipment";
        String headerLine = headerText + "-".repeat(WIDTH - headerText.length() - 2) + "*";

        // Liste der Ausrüstungsplätze
        String[] slots = {"Head:", "Body:", "Arms:", "Ring:", "Feet:"};

        // Items aus dem Player-Objekt holen (falls nichts ausgerüstet ist, soll "Empty" stehen)
        String[] equippedItems = {
                player.getHead() != null ? player.getHead().getName() : "Empty",
                player.getBody() != null ? player.getBody().getName() : "Empty",
                player.getArms() != null ? player.getArms().getName() : "Empty",
                player.getRing() != null ? player.getRing().getName() : "Empty",
                player.getFeet() != null ? player.getFeet().getName() : "Empty"
        };

        // Template dynamisch generieren
        List<String> template = new ArrayList<>();
        template.add(headerLine); // Header-Zeile

        // Füge Slots ein
        for (int i = 0; i < slots.length; i++) {
            String line = "| " + padEnd(slots[i], 6) + " " + padEnd(equippedItems[i], WIDTH - 12) + " |";
            template.add(line);
        }

        // **Auffüllen, damit die Gesamtgröße HEIGHT + 2 erreicht wird**
        while (template.size() < HEIGHT + 1) {  // **Nicht HEIGHT - 1!**
            template.add("| " + " ".repeat(WIDTH - 5) + " |");
        }

        template.add(borderLine); // Abschlusslinie

        return template.toArray(new String[0]);
    }




    public String[] renderItem(Item item, final int WIDTH, final int HEIGHT) {

        String borderLine = "*".repeat(WIDTH);
        String headerLine = "*-Item" + "-".repeat(WIDTH - 7) + "*";

        boolean itemSelected = item != null;
        // Header mit Item-Namen (Falls kein Item existiert, leeres Label)
        String itemName = itemSelected ? item.getName() : "No Item Selected";
        String nameLine = "| " + padEnd(itemName, WIDTH - 4) + " |"; // Breite anpassen

        // Feste Strings für Type & Rarity
        String type = itemSelected ? item.getCategory() : "";
        String rarity = itemSelected ? item.getRarity() : "";
        String typeRarityLine = "| " + padEnd(type, WIDTH / 2 - 2) + "| " + padEnd(rarity, WIDTH / 2 - 3) + " |";

        // Stats Header
        String statsHeader = "| " + " ".repeat(WIDTH - 4) + " |";

        String sellPrice = itemSelected ? "Sell Price: " + item.getSellPrice() : "";
        String sellPriceLine = "| " + padEnd(sellPrice, WIDTH - 4) + " |";

        String damageAmount = itemSelected ? item.doesDamage() ? "Damage: " + item.getDamage() : "" : "";
        String damageAmountLine = "| " + padEnd(damageAmount, WIDTH - 4) + " |";

        String healAmount = itemSelected ? item.givesHp() ? "Heal: " + item.getHealth() : "" : "";
        String healAmountLine = "| " + padEnd(healAmount, WIDTH - 4) + " |";

        String manaAmount = itemSelected ? item.givesMana() ? "Mana: " + item.getMana() : "" : "";
        String manaAmountLine = "| " + padEnd(manaAmount, WIDTH - 4) + " |";

        String[] stats = new String[]{sellPriceLine, damageAmountLine, healAmountLine, manaAmountLine};

        //  delete empty strings from stats[]
        List<String> statsList = new ArrayList<>(Arrays.asList(stats));

        // Actions Section
        String actionsHeader = "|*-Actions" + "-".repeat(WIDTH - 12) + "-|";
        List<String> actionsSection = new ArrayList<>();
        actionsSection.add("| SPACE to equip" + " ".repeat(WIDTH - 18) + " |");
        actionsSection.add("| Press D to drop" + " ".repeat(WIDTH - 19) + " |");

        // Restliche Leerzeilen für die Actions-Sektion (damit Höhe erreicht wird)
        int remainingLines = HEIGHT - (7 + statsList.size() + actionsSection.size());
        for (int i = 0; i < remainingLines - 1; i++) {
            actionsSection.add("| " + " ".repeat(WIDTH - 4) + " |");
        }

        // Rendering
        List<String> template = new ArrayList<>();
        template.add(headerLine);
        template.add(nameLine);
        template.add(borderLine);
        template.add(typeRarityLine);
        template.add(borderLine);
        template.add(statsHeader);
        template.addAll(statsList);
        template.add(actionsHeader);
        template.addAll(actionsSection);
        template.add(borderLine);

        return template.toArray(new String[0]);
    }

    public static String[] renderStats(final Player player, final int WIDTH, final int HEIGHT) {
        // Header & Border
        String borderLine = "*".repeat(WIDTH);
        String headerLine = "*-Stats" + "-".repeat(Math.max(0, WIDTH - 8)) + "*"; // -7 für "*-Stats"

        // Statistiken als einzeiliger String
        int hp = player.getHealth();
        int maxHp = player.getMaxHealth();
        int gold = player.getGold();
        String statsString = "HP: " + hp + "/" + maxHp + " | Gold: " + gold;

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

    public static String truncateString(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    public static String padEnd(String text, int length) {
        if (text.length() > length) {
            return text.substring(0, length); // Falls zu lang, abschneiden
        }
        return text + " ".repeat(length - text.length()); // Exakt auffüllen
    }

}
