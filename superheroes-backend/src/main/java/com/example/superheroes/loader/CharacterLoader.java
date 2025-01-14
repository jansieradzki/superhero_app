package com.example.superheroes.loader;

import com.example.superheroes.model.Character;
import com.example.superheroes.repository.CharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterLoader {
    private final CharacterRepository characterRepository;

    @PostConstruct
    public void loadCharacters() {
        if (characterRepository.count() > 0) {
            log.info("Characters already loaded. Skipping initialization.");
            return;
        }

        log.info("Loading characters from JSON...");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/characters.json");
            Map<String, List<Map<String, Object>>> data = objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, List.class));

            List<Map<String, Object>> items = data.get("items");
            List<Character> characters = new ArrayList<>();

            if (items != null) {
                for (Map<String, Object> item : items) {
                    String name = (String) item.get("name");
                    String type = (String) item.get("type");
                    double score = Double.parseDouble(item.get("score").toString());
                    String weakness = (String) item.get("weakness");

                    List<String> weaknesses = weakness != null ? List.of(weakness.split(" ")) : new ArrayList<>();

                    Character character = new Character(name, type, score, weaknesses);
                    characters.add(character);
                }

                characterRepository.saveAll(characters);
                log.info("Characters loaded successfully.");
            } else {
                log.warn("No characters found in JSON file.");
            }
        } catch (Exception e) {
            log.error("Failed to load characters from JSON file.", e);
        }
    }
}
