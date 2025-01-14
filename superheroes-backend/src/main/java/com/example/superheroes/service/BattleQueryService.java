package com.example.superheroes.service;

import com.example.superheroes.model.BattleHistoryDto;
import org.springframework.data.domain.Page;

/**
 * Service interface for querying battle history.
 */
public interface BattleQueryService {

    /**
     * Retrieves a paginated history of all battles.
     *
     * @param page the page number to retrieve (starting from 0)
     * @param size the number of records per page
     * @return a paginated {@link Page} of {@link BattleHistoryDto} records
     */
    Page<BattleHistoryDto> getBattleHistory(int page, int size);
}
