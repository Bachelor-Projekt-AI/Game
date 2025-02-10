package org.bachelorprojekt.game.events;

import org.bachelorprojekt.game.GameEvent;

import java.util.*;

/**
 * Verteilt Events an registrierte Listener.
 */
public class EventDispatcher {
    private static final Map<Class<? extends GameEvent>, List<EventListener>> listeners = new HashMap<>();

    /**
     * Registriert einen Listener für ein bestimmtes Event.
     */
    public static void registerListener(Class<? extends GameEvent> eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Entfernt einen Listener von einem Event-Typ.
     */
    public static void unregisterListener(Class<? extends GameEvent> eventType, EventListener listener) {
        if (listeners.containsKey(eventType)) {
            listeners.get(eventType).remove(listener);
        }
    }

    /**
     * Löst ein Event aus und benachrichtigt alle registrierten Listener.
     */
    public static void dispatchEvent(GameEvent event) {
        List<EventListener> registeredListeners = listeners.get(event.getClass());
        // sout all events
        System.out.println("Event: " + event.getClass().getSimpleName());
        if (registeredListeners != null) {
            for (EventListener listener : registeredListeners) {
                listener.onEvent(event);
            }
        }
    }
}
