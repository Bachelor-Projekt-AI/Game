package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.combat.CombatSystem;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;

import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bachelorprojekt.inventory.InventoryScreen.padEnd;
import static org.bachelorprojekt.util.Engine.getSpecialFont;

/**
 * Represents the combat menu screen, where the player can interact with the combat system,
 * choose items from their inventory, and execute actions during combat.
 */
public class CombatMenu extends ScreenAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Engine engine;
    private Player player;
    private int selectedIndex = -1;
    private final CombatSystem combatSystem;
    private List<Tuple<String, Color>> combatLog = new ArrayList<Tuple<String, Color>>();

    private int scrollOffset = 0; // Offset for visible items
    private static final int MAX_ITEMS = 6; // Maximum number of visible items

    /**
     * A simple tuple class used to store a pair of values.
     */
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

    /**
     * Constructs the combat menu screen with the given engine and combat system.
     *
     * @param engine The game engine instance for accessing resources like batch and font.
     * @param combatSystem The combat system used for managing the combat process.
     */
    public CombatMenu(Engine engine, CombatSystem combatSystem) {
        this.batch = engine.getBatch();
        this.combatSystem = combatSystem;
        combatSystem.startCombat();
        this.font = getSpecialFont();

        this.player = engine.getGameSystemManager().getPlayer();
        this.engine = engine;
    }

    /**
     * Logs a message to the combat log with the specified color. If the color is null, white is used.
     *
     * @param message The message to log.
     * @param color The color of the message text.
     */
    public void logMessage(String message, Color color) {
        if (combatLog.size() == 5) {
            combatLog.remove(0);
        }
        if (color == null) {
            color = Color.WHITE;
        }
        combatLog.add(new Tuple<String, Color>(message, color));
    }

    /**
     * Renders the combat menu, including the top combat information, inventory, and combat log.
     *
     * @param delta Time elapsed since the last frame, used for animations.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        int maxChars = 116; // Character width 16, 32 padding
        String[] combatTop = renderCombatTop(combatSystem.getPlayer().getName(), combatSystem.getPlayer().getHealth(), combatSystem.getEnemy().getName(), combatSystem.getEnemy().getHealth(), maxChars);
        String[] bp = renderCombatInventory(engine.getGameSystemManager().getPlayer().getInventory(), maxChars);
        int yPosition = 1060;
        for (String line : combatTop) {
            font.draw(batch, line, 32, yPosition);
            yPosition -= 40;
        }

        // Positioning the inventory section
        yPosition -= 20; // Space between sections
        for (String line : bp) {
            font.draw(batch, line, 32, yPosition);
            yPosition -= 40;
        }

        yPosition = 40;
        for (Tuple<String, Color> message : combatLog) {
            font.setColor(message.getRight());
            font.draw(batch, message.getLeft(), 32, yPosition);
            yPosition += 40;
        }
        font.setColor(Color.WHITE);

        batch.end();

        handleInput();
    }

    /**
     * Renders the top part of the combat screen, including player and enemy information, and their respective HP.
     *
     * @param playerName The player's name.
     * @param playerHP The player's current health.
     * @param enemyName The enemy's name.
     * @param enemyHP The enemy's current health.
     * @param WIDTH The width of the screen for layout purposes.
     * @return An array of strings representing the rendered combat top.
     */
    public static String[] renderCombatTop(String playerName, int playerHP, String enemyName, int enemyHP, final int WIDTH) {
        int sectionWidth = WIDTH / 2; // Left and right halves have equal width
        String fullLine = "*".repeat(WIDTH); // Full width for top and bottom boundaries

        // Placeholder strings for names
        String nameTemplate = "| PlayerPlaceholder" + " ".repeat(sectionWidth - "PlayerPlaceholder".length() - 3) +
                "| EnemyPlaceholder" + " ".repeat(sectionWidth - "EnemyPlaceholder".length() - 2) + "|";

        // Placeholder for HP line
        String hpTemplate = "| <3 HP: HPPlayerPlaceholder" + " ".repeat(sectionWidth - "<3 HP: HPPlayerPlaceholder".length() - 3) +
                "| <3 HP: HPEnemyPlaceholder" + " ".repeat(sectionWidth - "<3 HP: HPEnemyPlaceholder".length() - 2) + "|";

        // Static ASCII sprite
        String[] spriteTemplates = {
                "|     O" + " ".repeat(sectionWidth - 8) + "|     O" + " ".repeat(sectionWidth - 7) + "|",
                "|    /|\\" + " ".repeat(sectionWidth - 9) + "|    /|\\" + " ".repeat(sectionWidth - 8) + "|",
                "|    / \\" + " ".repeat(sectionWidth - 9) + "|    / \\" + " ".repeat(sectionWidth - 8) + "|"
        };

        // Dynamic replacement of placeholders
        nameTemplate = replaceAndPad(nameTemplate, "PlayerPlaceholder", playerName, sectionWidth);
        nameTemplate = replaceAndPad(nameTemplate, "EnemyPlaceholder", enemyName, sectionWidth);

        hpTemplate = replaceAndPad(hpTemplate, "HPPlayerPlaceholder", String.valueOf(playerHP), sectionWidth);
        hpTemplate = replaceAndPad(hpTemplate, "HPEnemyPlaceholder", String.valueOf(enemyHP), sectionWidth);

        List<String> output = new ArrayList<>();
        output.add(fullLine);
        output.add(nameTemplate);
        output.add(hpTemplate);
        output.addAll(Arrays.asList(spriteTemplates));
        output.add(fullLine);

        return output.toArray(new String[0]);
    }

    /**
     * Helper method to replace a placeholder in a template with a value and pad it to fit the section width.
     *
     * @param template The template string to modify.
     * @param placeholder The placeholder to replace.
     * @param value The value to replace the placeholder with.
     * @param sectionWidth The width of the section.
     * @return The modified template string.
     */
    private static String replaceAndPad(String template, String placeholder, String value, int sectionWidth) {
        int placeholderLength = placeholder.length();
        int valueLength = value.length();
        int spaceToFill = Math.max(0, placeholderLength - valueLength);

        return template.replace(placeholder, value + " ".repeat(spaceToFill));
    }

    /**
     * Resets the selected inventory index.
     */
    public void resetSelectedIndex() {
        selectedIndex = -1;
    }

    /**
     * Renders the combat inventory, showing available items and stats.
     *
     * @param weaponsAndPotions The list of items in the player's inventory.
     * @param WIDTH The width of the screen for layout purposes.
     * @return An array of strings representing the rendered inventory.
     */
    public String[] renderCombatInventory(List<Item> weaponsAndPotions, final int WIDTH) {
        Item selectedItem = selectedIndex >= 0 ? weaponsAndPotions.get(selectedIndex) : null;
        int LEFT_WIDTH = WIDTH / 2;
        int RIGHT_WIDTH = WIDTH / 2;
        int totalItems = weaponsAndPotions.size();

        // Limit the scroll offset
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

        // Display only visible items (scrollOffset controls the display)
        for (int i = 0; i < MAX_ITEMS; i++) {
            int itemIndex = scrollOffset + i;
            boolean isSelected = (itemIndex == selectedIndex);

            String itemName = itemIndex < weaponsAndPotions.size() ? weaponsAndPotions.get(itemIndex).getName() : "";
            String formattedLine = formatInventoryLine(itemName, itemStats, isSelected, LEFT_WIDTH, RIGHT_WIDTH);

            template.add(formattedLine);
        }

        template.add(borderLine);

        return template.toArray(new String[0]);
    }

    /**
     * Formats a line of the inventory to display both item name and associated stats.
     *
     * @param itemName The name of the item.
     * @param itemStats The stats of the selected item.
     * @param isSelected Whether the item is currently selected.
     * @param LEFT_WIDTH The width for the left section of the display.
     * @param RIGHT_WIDTH The width for the right section of the display.
     * @return A formatted string representing the inventory item and its stats.
     */
    private String formatInventoryLine(String itemName, List<String> itemStats, boolean isSelected, int LEFT_WIDTH, int RIGHT_WIDTH) {
        String itemNameFormatted = isSelected ? "*" + itemName : itemName;
        itemNameFormatted = padEnd(itemNameFormatted, LEFT_WIDTH - 3);

        String stats = itemStats.isEmpty() ? " " : String.join(", ", itemStats);
        stats = padEnd(stats, RIGHT_WIDTH - 3);

        return "| " + itemNameFormatted + "| " + stats + " |";
    }

    /**
     * Handles user input for selecting items from the inventory and executing actions.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = Math.max(0, selectedIndex - 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = Math.min(combatSystem.getPlayer().getInventory().size() - 1, selectedIndex + 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedIndex >= 0 && selectedIndex < combatSystem.getPlayer().getInventory().size()) {
                Item selectedItem = combatSystem.getPlayer().getInventory().get(selectedIndex);
                // Handle item usage logic here
            }
        }
    }
}
