package com.example.superheroes.service;

import com.example.superheroes.model.BattleHistory;
import com.example.superheroes.model.BattleHistoryDto;
import com.example.superheroes.repository.BattleHistoryRepository;
import com.example.superheroes.mapper.BattleHistoryMapper;
import com.example.superheroes.service.impl.BattleQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BattleQueryServiceTest {

    @Mock
    private BattleHistoryRepository battleHistoryRepository;

    @Mock
    private BattleHistoryMapper battleHistoryMapper;

    @InjectMocks
    private BattleQueryServiceImpl battleQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBattleHistory_shouldReturnPaginatedResults() {
        // Arrange
        BattleHistory battle1 = new BattleHistory(UUID.randomUUID(), "Batman", "Joker", "Batman", null);
        BattleHistory battle2 = new BattleHistory(UUID.randomUUID(), "Superman", "Lex Luthor", "Superman", null);
        BattleHistoryDto dto1 = new BattleHistoryDto(UUID.randomUUID(), "Batman", "Joker", "Batman", null);
        BattleHistoryDto dto2 = new BattleHistoryDto(UUID.randomUUID(), "Superman", "Lex Luthor", "Superman", null);

        Page<BattleHistory> page = new PageImpl<>(List.of(battle1, battle2));
        when(battleHistoryRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(battleHistoryMapper.toDto(battle1)).thenReturn(dto1);
        when(battleHistoryMapper.toDto(battle2)).thenReturn(dto2);

        // Act
        Page<BattleHistoryDto> result = battleQueryService.getBattleHistory(0, 10);

        // Assert
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).winner()).isEqualTo("Batman");
        assertThat(result.getContent().get(1).winner()).isEqualTo("Superman");
        verify(battleHistoryRepository).findAll(any(PageRequest.class));
        verify(battleHistoryMapper, times(2)).toDto(any(BattleHistory.class));
    }
}
