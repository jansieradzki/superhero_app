package com.example.superheroes.model;

import jakarta.validation.constraints.NotBlank;

public record CharacterDto(
        @NotBlank String name,
        @NotBlank String type,
        double score,
        String weakness
) {}