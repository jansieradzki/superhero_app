package com.example.superheroes.mapper;

import com.example.superheroes.model.BattleHistory;
import com.example.superheroes.model.BattleHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BattleHistoryMapper {
    BattleHistoryDto toDto(BattleHistory battleHistory);
}