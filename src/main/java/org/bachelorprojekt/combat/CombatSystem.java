package org.bachelorprojekt.combat;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.ui.CombatMenu;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Enemy;
import org.bachelorprojekt.util.json.jackson.Item;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Das Kampfsystem verwaltet den Kampf zwischen einem Spieler und einem Gegner.
 * Es steuert Angriff, Heilung und den Ablauf der Kampfrunden.
 */
public class CombatSystem {
    private final Engine engine;
    private final Player player;
    private final Enemy enemy;
    private final CombatMenu combatMenu;
    private boolean playerTurn;
    private boolean combatActive;
    private final Random random = new Random();

    /**
     * Erstellt eine neue Instanz des Kampfsystems.
     *
     * @param engine Die Spielengine.
     * @param player Der spielende Charakter.
     * @param enemy  Der Gegner.
     */
    public CombatSystem(Engine engine, Player player, Enemy enemy) {
        this.engine = engine;
        this.player = player;
        this.enemy = enemy;
        this.playerTurn = true;  // Spieler beginnt den Kampf
        this.combatActive = true;
        this.combatMenu = new CombatMenu(engine, this); // CombatMenu erhält CombatSystem-Referenz
        engine.pushScreen(combatMenu);
        engine.sendNotification(enemy.getRandomDialogue());
    }

    /**
     * Gibt den Gegner des Kampfes zurück.
     *
     * @return Der Gegner.
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Gibt den Spieler des Kampfes zurück.
     *
     * @return Der Spieler.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Startet den Kampf und gibt eine Konsolennachricht aus.
     */
    public void startCombat() {
        System.out.println(("🔴 Kampf gestartet: " + player.getName() + " vs. " + enemy.getName()));
    }

    /**
     * Führt einen Angriff des Spielers mit einer Waffe aus.
     *
     * @param weapon Die Waffe, mit der angegriffen wird.
     */
    public void playerAttack(Item weapon) {
        if (!playerTurn || !combatActive) return;

        int damage = weapon.getDamage();
        attack(player, enemy, damage, weapon.getName());

        if (!checkCombatEnd()) {
            playerTurn = false;
            enemyTurn();
        }
    }

    /**
     * Verwendet einen Heiltrank zur Wiederherstellung der Gesundheit.
     *
     * @param potion Der zu verwendende Heiltrank.
     */
    public void playerUsePotion(Item potion) {
        if (!playerTurn || !combatActive) return;

        usePotion(potion);

        if (!checkCombatEnd()) {
            playerTurn = false;
            enemyTurn();
        }
    }

    /**
     * Führt die Runde des Gegners aus. Der Gegner kann entweder angreifen oder sich heilen.
     */
    public void enemyTurn() {
        if (playerTurn || !combatActive) return;

        int damage = enemy.getBaseDamage();
        double healRate = enemy.getHealRate();

        // Gegner heilt sich mit einer bestimmten Wahrscheinlichkeit
        if (random.nextDouble() <= healRate) {
            int healAmount = enemy.getHealAmount();
            enemy.heal(healAmount);
            combatMenu.logMessage("🩹 " + enemy.getName() + " heilt sich um " + healAmount + " HP!", Color.RED);
        } else {
            attack(enemy, player, damage, "Angriff");
        }

        checkCombatEnd();
        playerTurn = true; // Spieler ist wieder dran
    }

    /**
     * Führt einen Angriff aus.
     *
     * @param attacker    Der Angreifer (Spieler oder Gegner).
     * @param defender    Der Verteidiger (Spieler oder Gegner).
     * @param damage      Der Schaden des Angriffs.
     * @param attackName  Der Name des Angriffs.
     */
    private void attack(Object attacker, Object defender, int damage, String attackName) {
        int attackRoll = random.nextInt(100) + 1;
        boolean hit = attackRoll <= 80; // 80% Trefferchance

        if (hit) {
            if (defender instanceof Player) {
                ((Player) defender).damage(damage);
            } else if (defender instanceof Enemy) {
                ((Enemy) defender).damage(damage);
            }

            combatMenu.logMessage("💥 " + getName(attacker) + " trifft mit " + attackName + " für " + damage + " Schaden!",
                    attacker instanceof Player ? Color.GREEN : Color.RED);
        } else {
            combatMenu.logMessage("❌ " + getName(attacker) + " verfehlt mit " + attackName + "!",
                    attacker instanceof Player ? Color.GREEN : Color.RED);
        }
    }

    /**
     * Gibt den Namen eines Angreifers oder Verteidigers zurück.
     *
     * @param obj Der Angreifer oder Verteidiger (Spieler oder Gegner).
     * @return Der Name des Objekts.
     */
    private static String getName(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).getName();
        } else if (obj instanceof Enemy) {
            return ((Enemy) obj).getName();
        }
        return "";
    }

    /**
     * Verwendet einen Heiltrank zur Wiederherstellung der Gesundheit.
     *
     * @param potion Der zu verwendende Heiltrank.
     */
    private void usePotion(Item potion) {
        if (!potion.givesHp()) return;
        int healAmount = potion.getHealth();
        player.heal(healAmount);
        combatMenu.logMessage("🩹 " + player.getName() + " trinkt " + potion.getName() + " und heilt " + healAmount + " HP!", Color.GREEN);
        combatMenu.resetSelectedIndex();
        player.getInventory().remove(potion);
    }

    /**
     * Überprüft, ob der Kampf beendet ist (Spieler oder Gegner besiegt).
     *
     * @return {@code true}, wenn der Kampf beendet ist, sonst {@code false}.
     */
    private boolean checkCombatEnd() {
        if (player.isDead()) {
            combatMenu.logMessage("💀 " + player.getName() + " wurde besiegt!", Color.RED);
            combatActive = false;
            engine.popScreen();
            engine.sendNotification("Du wurdest besiegt!");
            player.revive();
            return true;
        } else if (enemy.isDead()) {
            combatMenu.logMessage("🏆 " + enemy.getName() + " wurde besiegt!", Color.YELLOW);
            combatActive = false;
            engine.popScreen();
            engine.sendNotification("Du hast den Kampf gewonnen! Deine HP haben sich erholt.");
            player.revive();
            return true;
        }
        return false;
    }

    /**
     * Gibt zurück, ob der Spieler aktuell am Zug ist.
     *
     * @return {@code true}, wenn der Spieler am Zug ist, sonst {@code false}.
     */
    public boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * Gibt zurück, ob der Kampf aktiv ist.
     *
     * @return {@code true}, wenn der Kampf aktiv ist, sonst {@code false}.
     */
    public boolean isCombatActive() {
        return combatActive;
    }
}
