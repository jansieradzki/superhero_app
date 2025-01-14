package com.example.superheroes.service.impl;

import com.example.superheroes.mapper.BattleHistoryMapper;
import com.example.superheroes.model.BattleHistoryDto;
import com.example.superheroes.repository.BattleHistoryRepository;
import com.example.superheroes.service.BattleQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BattleQueryServiceImpl implements BattleQueryService {
    private final BattleHistoryRepository battleHistoryRepository;
    private final BattleHistoryMapper battleHistoryMapper;

    @Override
    public Page<BattleHistoryDto> getBattleHistory(int page, int size) {
        return battleHistoryRepository.findAll(PageRequest.of(page, size))
                .map(battleHistoryMapper::toDto);
    }
}