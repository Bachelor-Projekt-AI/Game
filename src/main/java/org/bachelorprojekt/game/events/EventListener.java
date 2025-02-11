package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

/**
 * Ein Interface für Objekte, die auf Events hören.
 */
public interface EventListener {
    void onEvent(GameEvent event);
}
