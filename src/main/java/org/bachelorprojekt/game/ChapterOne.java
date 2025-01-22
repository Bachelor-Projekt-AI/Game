package org.bachelorprojekt.game;

import org.bachelorprojekt.character.Player;

public class ChapterOne extends Chapter {
    public ChapterOne() {
        super("Kapitel 1", "Der Beginn einer Reise");
    }

    @Override
    public void start(Player player) {
        System.out.println("Kapitel 1 gestartet");
    }
}
