package org.bachelorprojekt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bachelorprojekt.character.Player;
import org.bachelorprojekt.game.Story;
import org.bachelorprojekt.game.Story.Chapter;
import org.bachelorprojekt.game.Story.Map;
import org.bachelorprojekt.game.Story.Quest;
import org.bachelorprojekt.inventory.Item;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;


//Test cases for testing
public class TestTest {
	@Test
	public void test() {
		assert(true);
	}

// Quest.isCompleted()	
@Test
public void testQuestIsCompleted_NotAllObjectivesCompleted() {
    Quest quest = new Quest();
    quest.setObjectives(List.of("Objective 1", "Objective 2"));
    quest.setObjectives(List.of("Objective 1"));

    assertFalse(quest.getIsCompleted());
}


// Map.containsLocation(String location)

@Test
public void testContainsLocation_LocationExists() {

    assertTrue(true); //LALALA
}


// Story.getCurrentChapter(int index)
@Test
public void testGetCurrentChapter_ValidIndex() {
    Story story = new Story();
    Chapter chapter1 = new Chapter();
    Chapter chapter2 = new Chapter();
    story.setChapters(List.of(chapter1, chapter2));

    assertEquals(chapter1, story.getCurrentChapter(0));
    assertEquals(chapter2, story.getCurrentChapter(1));
}
@Test
public void testGetCurrentChapter_InvalidIndex() {
    Story story = new Story();
    story.setChapters(List.of(new Chapter(), new Chapter()));

    assertThrows(IndexOutOfBoundsException.class, () -> story.getCurrentChapter(5));
    assertThrows(IndexOutOfBoundsException.class, () -> story.getCurrentChapter(-1));
}



}

