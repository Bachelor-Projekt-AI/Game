package org.bachelorprojekt.quest;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.EventDispatcher;
import org.bachelorprojekt.game.events.EventListener;
import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.*;

public class QuestSystem implements EventListener {
    private final Map<Class<? extends GameEvent>, List<QuestInstance>> questInstancesByEvent = new HashMap<>();
    private final List<QuestInstance> completedQuests = new ArrayList<>();
    private final Map<Integer, Quest> questRepository = new HashMap<>();
    private final Player player;

    /**
     * Initialisiert das Quest-System mit allen verfügbaren Quests.
     */
    public QuestSystem(List<Quest> allQuests, Player player) {
        loadAllQuests(allQuests);
        this.player = player;
    }

    /**
     * Lädt alle Quests in das Repository.
     */
    private void loadAllQuests(List<Quest> allQuests) {
        for (Quest quest : allQuests) {
            questRepository.put(quest.getId(), quest);
        }
    }

    /**
     * Startet eine neue Quest und registriert sie für das richtige Event.
     */
    public void startQuest(int questId) {
        Quest quest = questRepository.get(questId);
        if (quest != null) {
            QuestInstance instance = QuestFactory.createQuestInstance(quest);
            registerQuestInstance(instance);
            System.out.println("Quest gestartet: " + quest.getTitle());
        } else {
            System.out.println("Quest mit ID " + questId + " nicht gefunden.");
        }
    }

    /**
     * Registriert eine Quest für das richtige Event in der `questInstancesByEvent`-Map.
     */
    private void registerQuestInstance(QuestInstance instance) {
        Class<? extends GameEvent> eventType = instance.getTrigger().getEventType();
        questInstancesByEvent.computeIfAbsent(eventType, k -> new ArrayList<>());

        if (!questInstancesByEvent.get(eventType).contains(instance)) {  // Prüfen, ob bereits registriert
            questInstancesByEvent.get(eventType).add(instance);
        }

        EventDispatcher.registerListener(eventType, this);
    }


    /**
     * Wird vom EventDispatcher aufgerufen, wenn ein Event ausgelöst wird.
     */
    @Override
    public void onEvent(GameEvent event) {
        List<QuestInstance> relevantQuests = questInstancesByEvent.get(event.getClass());

        if (relevantQuests != null) {
            List<QuestInstance> toRemove = new ArrayList<>();

            for (QuestInstance questInstance : relevantQuests) {
                if (!questInstance.isCompleted() && questInstance.getTrigger().isTriggered(event)) {
                    completeQuest(questInstance);
                    toRemove.add(questInstance);
                }
            }

            // Entferne Quests aus der Map
            if (!toRemove.isEmpty()) {
                relevantQuests.removeAll(toRemove);

                // Falls keine aktiven Quests mehr für dieses Event existieren, lösche den Key aus der Map
                if (relevantQuests.isEmpty()) {
                    questInstancesByEvent.remove(event.getClass());
                    // TODO maybe dont do this because  all listeners are registered even tho there are no active quests
                    EventDispatcher.unregisterListener(event.getClass(), this);
                }
            }
        }
    }


    private void completeQuest(QuestInstance questInstance) {
        questInstance.complete();
        giveQuestRewards(questInstance);
        completedQuests.add(questInstance);
    }


    /**
     * Gibt dem Spieler automatisch die Belohnungen nach Abschluss der Quest.
     */
    private void giveQuestRewards(QuestInstance questInstance) {
        List<Item> rewardItems = questInstance.getQuestData().getRewardItems();

        if (!rewardItems.isEmpty()) {
            for (Item item : rewardItems) {
                player.addToInventory(item);
                System.out.println("Belohnung erhalten: " + item.getName());
            }
        } else {
            System.out.println("Keine Belohnungen für diese Quest.");
        }
    }

    public List<QuestInstance> getCompletedQuests() {
        return new ArrayList<>(completedQuests);
    }

    public List<QuestInstance> getActiveQuests() {
        return new ArrayList<>(questInstancesByEvent.values().stream().flatMap(List::stream).toList());
    }
}
