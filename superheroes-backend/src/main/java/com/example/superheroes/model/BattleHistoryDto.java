package com.example.superheroes.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record BattleHistoryDto(UUID id, String character, String rival, String winner, LocalDateTime timestamp) {}
