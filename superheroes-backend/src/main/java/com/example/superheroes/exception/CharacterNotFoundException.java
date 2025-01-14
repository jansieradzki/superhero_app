package com.example.superheroes.exception;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String characterName) {
        super("Character not found: " + characterName);
    }
}
