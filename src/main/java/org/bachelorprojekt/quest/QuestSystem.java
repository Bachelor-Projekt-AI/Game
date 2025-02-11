package org.bachelorprojekt.quest;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.GameEvent;
import org.bachelorprojekt.game.events.EventDispatcher;
import org.bachelorprojekt.game.events.EventListener;
import org.bachelorprojekt.quest.triggers.QuestTrigger;
import org.bachelorprojekt.ui.MessageScreen;
import org.bachelorprojekt.util.Engine;
import org.bachelorprojekt.util.json.jackson.Item;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.*;

public class QuestSystem implements EventListener {
    private final Map<Class<? extends GameEvent>, List<QuestInstance>> questInstancesByEvent = new HashMap<>();
    private final List<QuestInstance> completedQuests = new ArrayList<>();
    private final Map<Integer, QuestInstance> activeQuests = new HashMap<>();
    private final Map<Integer, Quest> questRepository = new HashMap<>();
    private final Player player;
    private final Engine engine;

    /**
     * Initialisiert das Quest-System mit allen verfügbaren Quests.
     */
    public QuestSystem(List<Quest> allQuests, Player player, Engine engine) {
        loadAllQuests(allQuests);
        this.player = player;
        this.engine = engine;
    }

    /**
     * Lädt alle Quests in das Repository.
     */
    private void loadAllQuests(List<Quest> allQuests) {
        for (Quest quest : allQuests) {
            questRepository.put(quest.getId(), quest);
        }
    }

    private boolean isQuestCompleted(int questId) {
        return completedQuests.stream().anyMatch(q -> q.getQuestData().getId() == questId);
    }

    /**
     * Startet eine neue Quest und registriert sie für das richtige Event.
     */
    public void startQuest(int questId) {
        Quest quest = questRepository.get(questId);

        if (isQuestCompleted(questId)) {
            System.out.println("⚠ Quest bereits abgeschlossen: " + quest.getTitle());
            return;
        }

        // Falls die Quest bereits aktiv ist, nicht erneut starten
        if (activeQuests.containsKey(questId) && activeQuests.get(questId).isActive()) {
            System.out.println("⚠ Quest ist bereits aktiv: " + quest.getTitle());
            return;
        }

        // Falls die Quest existiert, aber gestoppt wurde, reaktiviere sie
        QuestInstance instance = activeQuests.get(questId);
        if (instance != null) {
            instance.setIsActive(true);
            System.out.println("🔄 Quest wieder aktiviert: " + quest.getTitle());
            return;
        }

        // Falls noch keine Instanz existiert, erstelle eine neue
        if (quest != null) {
            instance = QuestFactory.createQuestInstance(quest);
            instance.setIsActive(true);
            activeQuests.put(questId, instance); // Wieder hinzufügen
            registerQuestInstance(instance);
            //engine.sendNotification("Quest gestartet: " + quest.getTitle());
            System.out.println("Quest gestartet: " + quest.getTitle());
        } else {
            System.out.println("Quest mit ID " + questId + " nicht gefunden.");
        }
    }




    public void stopQuest(int questId) {
        if (activeQuests.containsKey(questId)) {
            activeQuests.get(questId).setIsActive(false);
            activeQuests.remove(questId); // Aus activeQuests entfernen
        } else {
            System.out.println("⚠ Quest ist nicht aktiv oder existiert nicht.");
        }
    }




    private void registerQuestInstance(QuestInstance instance) {
        Class<? extends GameEvent> eventType = instance.getTrigger().getEventType();
        questInstancesByEvent.computeIfAbsent(eventType, k -> new ArrayList<>()).add(instance);
        EventDispatcher.registerListener(eventType, this);
    }

    @Override
    public void onEvent(GameEvent event) {
        List<QuestInstance> relevantQuests = questInstancesByEvent.get(event.getClass());

        if (relevantQuests != null) {
            Iterator<QuestInstance> iterator = relevantQuests.iterator();

            while (iterator.hasNext()) {
                QuestInstance questInstance = iterator.next();
                QuestTrigger trigger = questInstance.getTrigger();
                if (!questInstance.isCompleted() && questInstance.getTrigger().isTriggered(event) && questInstance.isActive()) {
                    int amount = trigger.getProgressAmount(event);  // Fortschrittsbetrag aus dem Trigger holen
                    updateProgress(questInstance, amount);

                    if (questInstance.isCompleted()) {
                        iterator.remove(); // Entferne die Quest aus der Event-Liste
                    }
                }
            }
        }
    }

    public void updateProgress(QuestInstance instance, int amount) {
        if (instance.isCompleted() || !instance.isActive()) return;

        instance.setProgress(instance.getProgress() + amount);
        Quest questData = instance.getQuestData();

        if (questData.getQuestType() == QuestType.COLLECT_ITEM) {
            if (instance.getProgress() >= questData.getRequiredAmount()) {
                completeQuest(instance);
            }
        } else if (questData.getQuestType() == QuestType.VISIT_LOCATION) {
            completeQuest(instance);
        } else if (questData.getQuestType() == QuestType.NPC_INTERACTION) {
            completeQuest(instance);
        }
    }

    private void completeQuest(QuestInstance instance) {
        if (instance.isCompleted() || !instance.isActive()) return; // Verhindert mehrfaches Abschließen

        instance.setIsActive(false);
        instance.setCompleted(true);
        giveQuestRewards(instance);
        completedQuests.add(instance);
        activeQuests.remove(instance.getQuestData().getId());
    }

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
        return new ArrayList<>(activeQuests.values());
    }

    public QuestInstance getQuestInstanceById(int questId) {
        // Erst in aktiven Quests suchen
        QuestInstance instance = activeQuests.get(questId);
        if (instance != null) {
            return instance;
        }

        // Falls nicht aktiv, in abgeschlossenen Quests suchen
        return completedQuests.stream()
                .filter(q -> q.getQuestData().getId() == questId)
                .findFirst()
                .orElse(null);
    }

}
