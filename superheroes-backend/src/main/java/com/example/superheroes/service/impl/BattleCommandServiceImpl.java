package com.example.superheroes.service.impl;

import com.example.superheroes.exception.CharacterNotFoundException;
import com.example.superheroes.mapper.BattleHistoryMapper;
import com.example.superheroes.model.BattleHistory;
import com.example.superheroes.model.BattleHistoryDto;
import com.example.superheroes.model.Character;
import com.example.superheroes.repository.BattleHistoryRepository;
import com.example.superheroes.repository.CharacterRepository;
import com.example.superheroes.service.BattleCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BattleCommandServiceImpl implements BattleCommandService {
    private final CharacterRepository characterRepository;
    private final BattleHistoryRepository battleHistoryRepository;
    private final BattleHistoryMapper battleHistoryMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${websocket.topic}")
    private String websocketTopic;

    @Override
    @Transactional
    public BattleHistoryDto battle(String characterName, String rivalName) {
        log.info("Starting battle between {} and {}", characterName, rivalName);

        Character character = characterRepository.findById(characterName)
                .orElseThrow(() -> new CharacterNotFoundException(characterName));

        Character rival = characterRepository.findById(rivalName)
                .orElseThrow(() -> new CharacterNotFoundException(rivalName));

        if (character.getType().equals(rival.getType())) {
            throw new IllegalArgumentException("Characters of the same type cannot fight.");
        }

        String winner = calculateWinner(character, rival);

        BattleHistory battleHistory = battleHistoryRepository.save(new BattleHistory(
                UUID.randomUUID(), characterName, rivalName, winner, LocalDateTime.now()
        ));

        BattleHistoryDto battleHistoryDto = battleHistoryMapper.toDto(battleHistory);

        messagingTemplate.convertAndSend(websocketTopic, battleHistoryDto);
        log.info("Battle result published to WebSocket: {}", battleHistoryDto);

        return battleHistoryDto;
    }

    private String calculateWinner(Character character, Character rival) {
        return character.getScore() > rival.getScore() ? character.getName() : rival.getName();
    }
}
