package org.bachelorprojekt.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import org.bachelorprojekt.util.Engine;

import java.util.ArrayList;
import java.util.List;

public class MessageScreen extends ScreenAdapter {
    private final Engine engine;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final String message;

    private long startTime; // Startzeit für das animierte Rendern
    private int currentCharIndex; // Aktuell sichtbare Zeichenanzahl
    private boolean textFullyRendered; // Status, ob der Text komplett sichtbar ist
    private boolean isFastForward; // Status, ob der Text schneller gerendert werden soll
    private static final float TEXT_SPEED_NORMAL = 30; // Normale Geschwindigkeit
    private static final float TEXT_SPEED_FAST = 5; // Schnelle Geschwindigkeit
    private float textSpeed = TEXT_SPEED_NORMAL;

    private final List<String> wrappedLines = new ArrayList<>(); // Zeilen mit Wortumbruch

    public MessageScreen(Engine engine, String message) {
        this.engine = engine;
        this.batch = engine.getBatch();
        this.font = engine.getFont();
        this.message = message;
        this.startTime = TimeUtils.millis();
        this.currentCharIndex = 0;
        this.textFullyRendered = false;
        this.isFastForward = false;

        wrapText(); // Text vorab umbrechen
    }

    @Override
    public void render(float delta) {
        // Den vorherigen Screen im Stack rendern (damit der Hintergrund sichtbar bleibt)
        if (engine.getScreenStack().size() > 1) {
            engine.getScreenStack().get(engine.getScreenStack().size() - 2).render(delta);
        }

        batch.begin();

        // Abdunkelung des Hintergrunds
        batch.setColor(0, 0, 0, 0.6f);
        batch.draw(engine.getWhiteTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Berechnung für das Modal (Fenster)
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int modalWidth = screenWidth / 2;
        int modalHeight = screenHeight / 3;
        int centerX = (screenWidth - modalWidth) / 2;
        int centerY = (screenHeight - modalHeight) / 2;

        // Modal mit blauem Rand
        batch.setColor(Color.BLUE);
        batch.draw(engine.getWhiteTexture(), centerX - 5, centerY - 5, modalWidth + 10, modalHeight + 10);
        batch.setColor(Color.BLACK);
        batch.draw(engine.getWhiteTexture(), centerX, centerY, modalWidth, modalHeight);

        // **Animierten Text rendern**
        drawAnimatedText(centerX + 20, centerY + modalHeight - 40);

        batch.end();

        handleInput();
    }

    private void wrapText() {
        // Maximale Textbreite im Modal
        float maxWidth = Gdx.graphics.getWidth() / 2f - 40;

        // Wortweiser Umbruch
        String[] words = message.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (font.getRegion().getTexture() == null) return; // Falls Font noch nicht geladen ist
            GlyphLayout layout = new GlyphLayout(font, line + " " + word);
            if (layout.width > maxWidth) {
                wrappedLines.add(line.toString());
                line.setLength(0);
            }
            if (!line.isEmpty()) line.append(" ");
            line.append(word);
        }
        if (!line.isEmpty()) wrappedLines.add(line.toString());
    }

    private void drawAnimatedText(float x, float startY) {
        // Berechne, wie viele Zeichen basierend auf der verstrichenen Zeit sichtbar sein sollten
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        currentCharIndex = Math.min((int) (elapsedTime / textSpeed), message.length());

        // Überprüfe, ob der Text vollständig gerendert wurde
        if (currentCharIndex == message.length()) {
            textFullyRendered = true;
        }

        // Zeichne nur den sichtbaren Teil des Textes, mit Zeilenumbruch
        float y = startY;
        int charsRendered = 0;
        for (String line : wrappedLines) {
            if (charsRendered >= currentCharIndex) break;

            int charsToShow = Math.min(line.length(), currentCharIndex - charsRendered);
            String visibleText = line.substring(0, charsToShow);
            GlyphLayout layout = new GlyphLayout(font, visibleText);

            // Zentrieren der Zeile
            float centeredX = x + (Gdx.graphics.getWidth() / 2f - layout.width) / 2;
            font.setColor(Color.WHITE);
            font.draw(batch, visibleText, centeredX, y);
            y -= 35; // Zeilenhöhe anpassen

            charsRendered += charsToShow;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (textFullyRendered) {
                engine.popScreen(); // Nachricht schließen
            } else {
                isFastForward = !isFastForward;
                textSpeed = isFastForward ? TEXT_SPEED_FAST : TEXT_SPEED_NORMAL;
            }
        }
    }
}
