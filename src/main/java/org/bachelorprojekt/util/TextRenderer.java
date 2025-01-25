package org.bachelorprojekt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import static java.awt.Font.createFont;

public class TextRenderer {
    private BitmapFont font;
    private SpriteBatch batch;

    private long startTime; // Zeitstempel für animiertes Rendern
    private int currentCharIndex; // Der aktuelle Index des zuletzt gerenderten Buchstabens

    public TextRenderer(BitmapFont font, SpriteBatch batch) {
        this.font = font;
        this.batch = batch;
        this.startTime = TimeUtils.millis(); // Initialisierung des Zeitstempels
        this.currentCharIndex = 0; // Start bei 0
    }

    // Zeichnet statischen Text, zentriert
    public void drawCenteredText(String text, float centerY) {
        GlyphLayout layout = new GlyphLayout(); // Hilfsklasse für genaue Textgröße
        float screenWidth = Gdx.graphics.getWidth(); // Bildschirmbreite abrufen

        String[] lines = text.split("\\n"); // Mehrzeiligen Text splitten
        for (int i = 0; i < lines.length; i++) {
            layout.setText(font, lines[i]); // Breite und Höhe für die aktuelle Zeile berechnen
            float textWidth = layout.width; // Tatsächliche Breite des Textes
            float centerX = (screenWidth - textWidth) / 2f; // Horizontale Mitte berechnen

            // Zeichne die aktuelle Zeile
            font.draw(batch, lines[i], centerX, centerY - (i * 30));
        }
    }



    // Zeichnet animierten Text, Buchstabe für Buchstabe
    public void drawAnimatedText(String text, float x, float y, float speed) {
        // Initialisiere startTime nur einmal
        if (startTime == 0) {
            startTime = TimeUtils.millis();
        }

        // Berechne die Anzahl der Zeichen, die basierend auf der verstrichenen Zeit angezeigt werden
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        currentCharIndex = Math.min((int) (elapsedTime / speed), text.length());

        // Render den sichtbaren Text bis zum aktuellen Index
        String visibleText = text.substring(0, currentCharIndex);
        font.draw(batch, visibleText, x, y);
    }

    // Reset für animiertes Rendern, um von vorne zu starten
    public void resetAnimation() {
        this.startTime = TimeUtils.millis();
        this.currentCharIndex = 0;
    }
}
