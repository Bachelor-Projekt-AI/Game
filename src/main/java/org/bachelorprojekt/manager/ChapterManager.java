package org.bachelorprojekt.manager;

import org.bachelorprojekt.util.json.jackson.Chapter;
import org.bachelorprojekt.util.json.jackson.Location;
import org.bachelorprojekt.util.json.jackson.Maps;
import org.bachelorprojekt.util.json.jackson.Quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChapterManager {

    private final java.util.Map<Integer, Chapter> chapters;

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

    public Chapter getChapterById(int chapterId) {
        return chapters.get(chapterId);
    }

    public List<Chapter> getAllChapters() {
        return List.copyOf(chapters.values());
    }

    public boolean chapterExists(int chapterId) {
        return chapters.containsKey(chapterId);
    }
}
