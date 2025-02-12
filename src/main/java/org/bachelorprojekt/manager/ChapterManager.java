package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Maps;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the chapters in the application, providing methods to retrieve chapters by ID, 
 * check if a chapter exists, and get all chapters.
 */
public class ChapterManager {

    private final java.util.Map<Integer, Chapter> chapters;

    /**
     * Constructs a ChapterManager instance, initializing the chapters from the provided lists.
     * The chapters are mapped by their ID, and each chapter is initialized with corresponding
     * quest, location, and map data.
     *
     * @param chapterList A list of chapters to be managed.
     * @param questList A list of quests to be associated with the chapters.
     * @param locationList A list of locations to be associated with the chapters.
     * @param mapList A list of maps to be associated with the chapters.
     */
    public ChapterManager(List<Chapter> chapterList, List<Quest> questList, List<Location> locationList, List<Maps> mapList) {
        this.chapters = new HashMap<>();

        Map<Integer, Quest> questMap = questList.stream().collect(Collectors.toMap(Quest::getId, quest -> quest));
        Map<Integer, Location> locationMap = locationList.stream().collect(Collectors.toMap(Location::getId, location -> location));
        Map<Integer, Maps> mapMap = mapList.stream().collect(Collectors.toMap(Maps::getId, map -> map));

        for (Chapter chapter : chapterList) {
            chapter.initMapping(questMap, locationMap, mapMap);
            chapters.put(chapter.getId(), chapter);
        }
    }

    /**
     * Retrieves a chapter by its ID.
     *
     * @param chapterId The ID of the chapter to retrieve.
     * @return The chapter corresponding to the given ID, or null if not found.
     */
    public Chapter getChapterById(int chapterId) {
        return chapters.get(chapterId);
    }

    /**
     * Retrieves all chapters managed by this ChapterManager.
     *
     * @return A list of all chapters.
     */
    public List<Chapter> getAllChapters() {
        return List.copyOf(chapters.values());
    }

    /**
     * Checks if a chapter with the given ID exists.
     *
     * @param chapterId The ID of the chapter to check.
     * @return True if a chapter with the given ID exists, false otherwise.
     */
    public boolean chapterExists(int chapterId) {
        return chapters.containsKey(chapterId);
    }
}
