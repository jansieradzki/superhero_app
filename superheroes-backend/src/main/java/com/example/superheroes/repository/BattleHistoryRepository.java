package com.example.superheroes.repository;

import com.example.superheroes.model.BattleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BattleHistoryRepository extends JpaRepository<BattleHistory, UUID> {
}