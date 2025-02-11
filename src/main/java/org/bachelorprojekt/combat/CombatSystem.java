package org.bachelorprojekt.combat;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.ui.CombatMenu;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Enemy;
import org.bachelorprojekt.util.json.jackson.Item;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class CombatSystem {
    private final Engine engine;
    private final Player player;
    private final Enemy enemy;
    private final CombatMenu combatMenu;
    private boolean playerTurn;
    private boolean combatActive;
    private final Random random = new Random();

    public CombatSystem(Engine engine, Player player, Enemy enemy) {
        this.engine = engine;
        this.player = player;
        this.enemy = enemy;
        this.playerTurn = true;  // Spieler beginnt den Kampf
        this.combatActive = true;
        this.combatMenu = new CombatMenu(engine, this); // CombatMenu erhält CombatSystem-Referenz
        engine.pushScreen(combatMenu);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Player getPlayer() {
        return player;
    }

    public void startCombat() {
        System.out.println(("🔴 Kampf gestartet: " + player.getName() + " vs. " + enemy.getName()));
    }

    public void playerAttack(Item weapon) {
        if (!playerTurn || !combatActive) return; // Falls nicht am Zug, ignoriere Input

        int damage = weapon.getDamage();
        attack(player, enemy, damage, weapon.getName());

        // Kampfende prüfen, bevor der Gegner agiert
        if (!checkCombatEnd()) {
            playerTurn = false;
            enemyTurn();
        }
    }

    public void playerUsePotion(Item potion) {
        if (!playerTurn || !combatActive) return;

        usePotion(potion);

        // Kampfende prüfen, bevor der Gegner agiert
        if (!checkCombatEnd()) {
            playerTurn = false;
            enemyTurn();
        }
    }


    public void enemyTurn() {
        if (playerTurn || !combatActive) return; // Nur wenn Gegner am Zug ist

        int damage = enemy.getBaseDamage();
        int bonusDamage = enemy.getBonusDamage();
        double healRate = enemy.getHealRate();

        // heal roll with healRate
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

    private static String getName(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).getName();
        } else if (obj instanceof Enemy) {
            return ((Enemy) obj).getName();
        }
        return "";
    }

    private void usePotion(Item potion) {
        if (!potion.givesHp()) return; // Nur Heiltränke verwenden
        int healAmount = potion.getHealth();
        player.heal(healAmount);
        combatMenu.logMessage("🩹 " + player.getName() + " trinkt " + potion.getName() + " und heilt " + healAmount + " HP!", Color.GREEN);
        combatMenu.resetSelectedIndex();
        player.getInventory().remove(potion); // Trank verbrauchen
    }

    private boolean checkCombatEnd() {
        System.out.println(player.isDead());
        System.out.println(player.getHealth());
        if (player.isDead()) {
            combatMenu.logMessage("💀 " + player.getName() + " wurde besiegt!", Color.RED);
            combatActive = false;
            engine.popScreen(); // Kampf beenden
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


    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isCombatActive() {
        return combatActive;
    }
}
