package com.example.superheroes.service;

import com.example.superheroes.exception.CharacterNotFoundException;
import com.example.superheroes.model.BattleHistory;
import com.example.superheroes.model.BattleHistoryDto;
import com.example.superheroes.model.Character;
import com.example.superheroes.repository.BattleHistoryRepository;
import com.example.superheroes.repository.CharacterRepository;
import com.example.superheroes.mapper.BattleHistoryMapper;
import com.example.superheroes.service.impl.BattleCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BattleCommandServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private BattleHistoryRepository battleHistoryRepository;

    @Mock
    private BattleHistoryMapper battleHistoryMapper;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private BattleCommandServiceImpl battleCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(battleCommandService, "websocketTopic", "/topic/battle-results");
        doNothing().when(messagingTemplate).convertAndSend(anyString(), any(BattleHistoryDto.class));
    }

    @Test
    void battle_shouldReturnWinner_whenValidCharacters() {
        // Arrange
        Character batman = new Character("Batman", "hero", 8.3, List.of("Joker"));
        Character joker = new Character("Joker", "villain", 8.2, List.of());

        BattleHistory mockBattleHistory = new BattleHistory(UUID.randomUUID(), "Batman", "Joker", "Batman", null);
        BattleHistoryDto mockBattleHistoryDto = new BattleHistoryDto(mockBattleHistory.getId(), "Batman", "Joker", "Batman", null);

        when(characterRepository.findById("Batman")).thenReturn(Optional.of(batman));
        when(characterRepository.findById("Joker")).thenReturn(Optional.of(joker));
        when(battleHistoryRepository.save(any(BattleHistory.class))).thenReturn(mockBattleHistory);
        when(battleHistoryMapper.toDto(mockBattleHistory)).thenReturn(mockBattleHistoryDto);

        // Act
        BattleHistoryDto result = battleCommandService.battle("Batman", "Joker");

        // Assert
        assertThat(result.winner()).isEqualTo("Batman");
        verify(battleHistoryRepository).save(any(BattleHistory.class));
        verify(battleHistoryMapper).toDto(mockBattleHistory);
        verify(messagingTemplate).convertAndSend(contains("/topic/battle-results"), eq(mockBattleHistoryDto));
    }


    @Test
    void battle_shouldThrowException_whenCharactersOfSameType() {
        // Arrange
        Character batman = new Character("Batman", "hero", 8.3, List.of("Joker"));
        Character superman = new Character("Superman", "hero", 9.6, List.of("Lex Luthor"));

        when(characterRepository.findById("Batman")).thenReturn(Optional.of(batman));
        when(characterRepository.findById("Superman")).thenReturn(Optional.of(superman));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                battleCommandService.battle("Batman", "Superman"));
        assertThat(exception.getMessage()).isEqualTo("Characters of the same type cannot fight.");
    }

    @Test
    void battle_shouldThrowException_whenCharacterNotFound() {
        // Arrange
        when(characterRepository.findById("NonExistent"))
                .thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(CharacterNotFoundException.class, () ->
                battleCommandService.battle("NonExistent", "Joker"));
        assertThat(exception.getMessage()).isEqualTo("Character not found: NonExistent");
    }
}
