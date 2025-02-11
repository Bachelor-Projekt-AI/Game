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
        engine.setNotificationActive(true);
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
        batch.draw(engine.getWhiteTexture(), 0, 0, 1920, 1080);

        // Berechnung für das Modal (Fenster)
        int modalWidth = 960;
        int modalHeight = 360;
        int centerX = 480;
        int centerY = 360;

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
        // Wortweiser Umbruch
        String[] words = message.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (font.getRegion().getTexture() == null) return; // Falls Font noch nicht geladen ist
            GlyphLayout layout = new GlyphLayout(font, line + " " + word);
            if (layout.width > 920) {
                wrappedLines.add(line.toString());
                line.setLength(0);
            }
            if (!line.isEmpty()) line.append(" ");
            line.append(word);
        }
        if (!line.isEmpty()) wrappedLines.add(line.toString());
    }

    private void drawAnimatedText(float x, float startY) {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        currentCharIndex = Math.min((int) (elapsedTime / textSpeed), message.length());

        if (currentCharIndex == message.length()) {
            textFullyRendered = true;
        }

        float y = startY;
        int charsRendered = 0;
        float textPadding = 20; // Abstand vom Rand des Modals

        for (String line : wrappedLines) {
            if (charsRendered >= currentCharIndex) break;

            int charsToShow = Math.min(line.length(), currentCharIndex - charsRendered);
            String visibleText = line.substring(0, charsToShow);
            GlyphLayout layout = new GlyphLayout(font, visibleText);

            // **Fix:** Zentriere nur innerhalb des Modalfensters!
            float centeredX = x + (960 - layout.width) / 2 - textPadding;

            font.setColor(Color.WHITE);
            font.draw(batch, visibleText, centeredX, y);
            y -= 35;

            charsRendered += charsToShow;
        }
    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (textFullyRendered) {
                engine.popScreen(); // Nachricht schließen
                engine.setNotificationActive(false);
            } else {
                isFastForward = !isFastForward;
                textSpeed = isFastForward ? TEXT_SPEED_FAST : TEXT_SPEED_NORMAL;
            }
        }
    }
}
