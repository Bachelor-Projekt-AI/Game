package org.bachelorprojekt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bachelorprojekt.game.Story;

public class JsonLoader {
    public static Story loadStory(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            FileHandle file = Gdx.files.internal(filePath); // Zugriff auf die JSON-Datei
            return mapper.readValue(file.reader(), Story.class); // JSON zu Story parsen
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load story: " + e.getMessage());
        }
    }
}
