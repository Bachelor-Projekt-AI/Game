package org.bachelorprojekt.ui;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.bachelorprojekt.util.Engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene extends ScreenAdapter {

    protected final Engine engine;
    protected final SpriteBatch batch;
    protected final BitmapFont font;

    private final List<IRenderable> elements = new ArrayList<>();

    protected Scene(Engine engine) {
        this.engine = engine;
        this.batch = engine.getBatch();
        this.font = engine.getFont();
    }

    /**
     * Fügt ein neues renderbares Element zur Szene hinzu.
     */
    protected void addRenderableElement(IRenderable element) {
        elements.add(element);
    }

    /**
     * Zeichnet alle registrierten renderbaren Elemente.
     */
    protected void renderElements() {
        batch.begin();
        for (IRenderable element : elements) {
            element.render(batch, font);
        }
        batch.end();
    }

    /**
     * Abstrakte Methoden für Eingabeverarbeitung und Szenen-Logik.
     */
    public abstract void handleInput();

    @Override
    public void render(float delta) {
        super.render(delta);
        renderElements();
    }
}
