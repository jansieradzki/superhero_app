package com.example.superheroes.service;

import com.example.superheroes.model.BattleHistoryDto;

/**
 * Service interface for handling battle operations.
 */
public interface BattleCommandService {

    /**
     * Executes a battle between two characters and determines the winner.
     *
     * @param characterName the name of the superhero initiating the battle
     * @param rivalName the name of the supervillain to battle against
     * @return a {@link BattleHistoryDto} containing the details of the battle and the winner
     */
    BattleHistoryDto battle(String characterName, String rivalName);
}
