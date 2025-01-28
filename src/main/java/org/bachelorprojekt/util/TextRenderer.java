package org.bachelorprojekt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class TextRenderer {
    private BitmapFont font;
    private SpriteBatch batch;

    private long startTime; // Zeitstempel für animiertes Rendern
    private int currentCharIndex; // Der aktuelle Index des zuletzt gerenderten Buchstabens

    private int screenWidth;
    private int screenHeight;

    public TextRenderer(Engine engine) {
        this.font = engine.getFont();
        this.batch = engine.getBatch();
        this.startTime = TimeUtils.millis(); // Initialisierung des Zeitstempels
        this.currentCharIndex = 0; // Start bei 0
        updateScreenDimensions(); // Bildschirmdimensionen initialisieren
    }

    // Methode zum Aktualisieren der Bildschirmdimensionen (z. B. bei Skalierung)
    public void updateScreenDimensions() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
    }

    // Zeichnet Text an einer festen Pixelposition
    public void drawText(String text, float x, float y) {
        font.draw(batch, text, x, y);
    }

    // Zeichnet Text zentriert auf den Bildschirm
    public void drawCenteredText(String text, float centerY, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(); // Hilfsklasse für genaue Textgröße

        String[] lines = text.split("\\n"); // Mehrzeiligen Text splitten
        for (int i = 0; i < lines.length; i++) {
            layout.setText(font, lines[i]); // Breite und Höhe für die aktuelle Zeile berechnen
            float textWidth = layout.width; // Tatsächliche Breite des Textes
            float centerX = (screenWidth - textWidth) / 2f; // Horizontale Mitte berechnen

            // Zeichne die aktuelle Zeile
            font.draw(batch, lines[i], centerX, centerY - (i * 30));
        }
    }

    // Zeichnet Text basierend auf Prozentwerten der Bildschirmdimensionen
    public void drawTextAtPercent(String text, float percentX, float percentY) {
        float x = screenWidth * (percentX / 100f);
        float y = screenHeight * (percentY / 100f);
        drawText(text, x, y);
    }

    // Zeichnet animierten Text, Buchstabe für Buchstabe
    public void drawAnimatedText(String text, float percentX, float percentY, float speed) {
        // Initialisiere startTime nur einmal
        if (startTime == 0) {
            startTime = TimeUtils.millis();
        }

        // Berechne die Anzahl der Zeichen, die basierend auf der verstrichenen Zeit angezeigt werden
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        currentCharIndex = Math.min((int) (elapsedTime / speed), text.length());

        // Render den sichtbaren Text bis zum aktuellen Index
        String visibleText = text.substring(0, currentCharIndex);
        float x = screenWidth * (percentX / 100f);
        float y = screenHeight * (percentY / 100f);
        font.draw(batch, visibleText, x, y);
    }

    // Reset für animiertes Rendern, um von vorne zu starten
    public void resetAnimation() {
        this.startTime = TimeUtils.millis();
        this.currentCharIndex = 0;
    }

    // Zeichnet eine Box mit Text zentriert
    public void drawTextBox(String text, float percentX, float percentY, float widthPercent) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);

        float boxWidth = screenWidth * (widthPercent / 100f);
        float x = screenWidth * (percentX / 100f) - boxWidth / 2f;
        float y = screenHeight * (percentY / 100f);

        font.draw(batch, text, x, y);
    }
}
