package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.combat.CombatSystem;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.lwjgl.opengl.GL20;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bachelorprojekt.inventory.InventoryScreen.padEnd;
import static org.bachelorprojekt.util.Engine.getSpecialFont;

public class CombatMenu extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Engine engine;
    private Player player;
    private int selectedIndex = -1;
    private final CombatSystem combatSystem;
	private List<Tuple<String, Color>> combatLog = new ArrayList<Tuple<String, Color>>();

    private int scrollOffset = 0; // Offset für sichtbare Items
    private static final int MAX_ITEMS = 6; // Maximal 6 sichtbare Items

    public CombatMenu(Engine engine, CombatSystem combatSystem) {
        this.batch = engine.getBatch();
        this.combatSystem = combatSystem;
        combatSystem.startCombat();
        //this.font = engine.getFont();

        //this.font = engine.loadFont("fonts/JetBrainsMono-Regular.ttf", 22);

        this.font = getSpecialFont();


        this.player = engine.getGameSystemManager().getPlayer();
        this.engine = engine;
    }

    public void logMessage(String message, Color color) {
        if (combatLog.size() >= 5) {
            combatLog.removeFirst();
        }
		if (color == null) {
			color = Color.WHITE;
		}
        combatLog.add(new Tuple(message, color));
    }

    public static int getMaxCharsPerLine(BitmapFont font) {
        float screenWidth = 1920;
        GlyphLayout layout = new GlyphLayout(font, "W"); // Ein Zeichen ausmessen
        float charWidth = layout.width; // Breite eines Zeichens

        return (int) (screenWidth / charWidth) - 10; // Maximale Zeichenanzahl pro Zeile mit padding
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
        int maxChars = getMaxCharsPerLine(font);
        String[] combatTop = renderCombatTop(combatSystem.getPlayer().getName(), combatSystem.getPlayer().getHealth(), combatSystem.getEnemy().getName(), combatSystem.getEnemy().getHealth(), maxChars);
        String[] bp = renderCombatInventory(engine.getGameSystemManager().getPlayer().getInventory(), maxChars);
        int yPosition = 1060;
        for (String line : combatTop) {
            font.draw(batch, line, 20, yPosition);
            yPosition -= 40;
        }

        // Positionierung (Untere Hälfte)
        yPosition -= 20; // Abstand
        for (String line : bp) {
            font.draw(batch, line, 20, yPosition);
            yPosition -= 40;
        }

		yPosition = 40;
		for (Tuple<String, Color> message : combatLog.reversed()) {
			font.setColor(message.getRight());
			font.draw(batch, message.getLeft(), 20, yPosition);
			yPosition += 40;
		}
		font.setColor(Color.WHITE);

        batch.end();

        handleInput();
    }

    public static String[] renderCombatTop(String playerName, int playerHP, String enemyName, int enemyHP, final int WIDTH) {
        int sectionWidth = WIDTH / 2; // Linke und rechte Hälfte haben die gleiche Breite
        String fullLine = "*".repeat(WIDTH); // Volle Breite für obere und untere Begrenzung

        // Platzhalter-Strings für die Namen
        String nameTemplate = "| PlayerPlaceholder" + " ".repeat(sectionWidth - "PlayerPlaceholder".length() - 3) +
                "| EnemyPlaceholder" + " ".repeat(sectionWidth - "EnemyPlaceholder".length() - 2) + "|";

        // Platzhalter für die HP-Zeile
        String hpTemplate = "| <3 HP: HPPlayerPlaceholder" + " ".repeat(sectionWidth - "<3 HP: HPPlayerPlaceholder".length() - 3) +
                "| <3 HP: HPEnemyPlaceholder" + " ".repeat(sectionWidth - "<3 HP: HPEnemyPlaceholder".length() - 2) + "|";

        // Statischer ASCII-Sprite
        String[] spriteTemplates = {
                "|     O" + " ".repeat(sectionWidth - 8) + "|     O" + " ".repeat(sectionWidth - 7) + "|",
                "|    /|\\" + " ".repeat(sectionWidth - 9) + "|    /|\\" + " ".repeat(sectionWidth - 8) + "|",
                "|    / \\" + " ".repeat(sectionWidth - 9) + "|    / \\" + " ".repeat(sectionWidth - 8) + "|"
        };

        // Dynamische Anpassung: Namen + Leerzeichen-Auffüllung
        nameTemplate = replaceAndPad(nameTemplate, "PlayerPlaceholder", playerName, sectionWidth);
        nameTemplate = replaceAndPad(nameTemplate, "EnemyPlaceholder", enemyName, sectionWidth);

        // Dynamische Anpassung: HP-Werte + Leerzeichen-Auffüllung
        hpTemplate = replaceAndPad(hpTemplate, "HPPlayerPlaceholder", String.valueOf(playerHP), sectionWidth);
        hpTemplate = replaceAndPad(hpTemplate, "HPEnemyPlaceholder", String.valueOf(enemyHP), sectionWidth);

        // Aufbau der finalen Darstellung
        List<String> output = new ArrayList<>();
        output.add(fullLine);
        output.add(nameTemplate);
        output.add(hpTemplate);
        output.addAll(Arrays.asList(spriteTemplates));
        output.add(fullLine);

        return output.toArray(new String[0]);
    }

    // **Helfer-Methode zur dynamischen Platzhalter-Ersetzung + Auffüllung mit Leerzeichen**
    private static String replaceAndPad(String template, String placeholder, String value, int sectionWidth) {
        int placeholderLength = placeholder.length();
        int valueLength = value.length();
        int spaceToFill = Math.max(0, placeholderLength - valueLength);

        return template.replace(placeholder, value + " ".repeat(spaceToFill));
    }



    public void resetSelectedIndex() {
        selectedIndex = -1;
    }




    public String[] renderCombatInventory(List<Item> weaponsAndPotions, final int WIDTH) {
        int totalLines = getMaxLinesOnScreen(font) - 10;
        int halfLines = totalLines / 2;
        Item selectedItem = selectedIndex >= 0 ? weaponsAndPotions.get(selectedIndex) : null;
        int LEFT_WIDTH = WIDTH / 2;
        int RIGHT_WIDTH = WIDTH / 2;
        int totalItems = weaponsAndPotions.size();

        // Begrenze den Scroll-Offset
        scrollOffset = Math.max(0, Math.min(scrollOffset, Math.max(0, totalItems - MAX_ITEMS)));

        // Header & Border
        String borderLine = "*" + "-".repeat(WIDTH - 2) + "*";
        String headerLine = "| " + padEnd("Inventory", LEFT_WIDTH - 3) + "| " + padEnd("Stats", RIGHT_WIDTH - 3) + " |";

        List<String> template = new ArrayList<>();
        template.add(borderLine);
        template.add(headerLine);
        template.add(borderLine);

        List<String> itemStats = new ArrayList<>();

        if (selectedItem != null) {
            itemStats.add("Name: " + selectedItem.getName());
            itemStats.add("Description: " + selectedItem.getDescription());
            if (selectedItem.givesHp()) {
                itemStats.add("Heal: " + selectedItem.getHealth());
            }
            if (selectedItem.doesDamage()) {
                itemStats.add("Damage: " + selectedItem.getDamage());
            }
            if (selectedItem.givesMana()) {
                itemStats.add("Mana: " + selectedItem.getMana());
            }
        } else {
            itemStats.add("No Item Selected");
        }

        // Zeige nur sichtbare Items (scrollOffset steuert die Anzeige)
        for (int i = 0; i < MAX_ITEMS; i++) {
            int itemIndex = scrollOffset + i;
            boolean isSelected = (itemIndex == selectedIndex);

            String itemName = itemIndex < totalItems ? weaponsAndPotions.get(itemIndex).getName() : "";
            String prefix = isSelected ? "> " : "  "; // Markiere das ausgewählte Item

            // **Fix: Pad auf feste Breite, damit Pipes (|) nicht verschoben werden**
            String rightSide = "| " + padEnd((i < itemStats.size() ? itemStats.get(i) : ""), RIGHT_WIDTH - 3) + " |";
            String leftSide = "| " + padEnd(prefix + itemName, LEFT_WIDTH - 3);

            template.add(leftSide + rightSide);
        }

        template.add(borderLine);

        // Kontextmenü (Aktionen)
        template.add("| " + padEnd("Actions", LEFT_WIDTH - 3) + "| " + padEnd("", RIGHT_WIDTH - 3) + " |");
        template.add("| " + padEnd("SPACE to attack", LEFT_WIDTH - 3) + "| " + padEnd("Press D to drop", RIGHT_WIDTH - 3) + " |");
        template.add("| " + padEnd("Press X to see stats", LEFT_WIDTH - 3) + "| " + padEnd("Press Q to surrender", RIGHT_WIDTH - 3) + " |");
        template.add(borderLine);

        return template.toArray(new String[0]);
    }





    protected void handleInput() {
        int totalItems = player.getInventory().size();

        // Nach oben scrollen
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (selectedIndex > 0) {
                selectedIndex--;
                if (selectedIndex < scrollOffset) {
                    scrollOffset = Math.max(0, scrollOffset - 1);
                }
            }
        }

        // Nach unten scrollen
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (selectedIndex < totalItems - 1) {
                selectedIndex++;
                if (selectedIndex >= scrollOffset + MAX_ITEMS) {
                    scrollOffset = Math.min(totalItems - MAX_ITEMS, scrollOffset + 1);
                }
            }
        }

        // Auswahl bestätigen (z. B. Angriff)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (!player.getInventory().isEmpty()) {
                Item selectedItem = player.getInventory().get(selectedIndex);
                if (selectedItem.getCategory().equals("weapon")) {
                    combatSystem.playerAttack(selectedItem);
                } else if (selectedItem.getCategory().equals("consumable")) {
                    combatSystem.playerUsePotion(selectedItem);
                }
            }
        }


        // Inventar schließen
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            engine.popScreen();
            System.out.println("Inventory closed.");
        }
    }

	private class Tuple<T, U> {
		final T left;
		final U right;

		Tuple (T left, U right) {
			this.left = left;
			this.right = right;
		}

		public T getLeft() {
			return left;
		}

		public U getRight() {
			return right;
		}
	}
}
