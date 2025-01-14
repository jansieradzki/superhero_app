package com.example.superheroes.integration;

import com.example.superheroes.model.BattleHistoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BattleControllerIntegrationTest extends DatabaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String validCharacter = "Batman";
    private final String validRival = "Joker";

    private static final String BATTLE_ENDPOINT = "/api/battle";
    private static final String BATTLE_HISTORY_ENDPOINT = "/api/battle_history";

    @Test
    void shouldPerformBattleBetweenTwoValidCharacters() {
        // given
        String characterName = validCharacter;
        String rivalName = validRival;

        // when
        ResponseEntity<BattleHistoryDto> response = restTemplate.exchange(
                BATTLE_ENDPOINT + "?character={chara}&rival={riv}",
                HttpMethod.GET,
                null,
                BattleHistoryDto.class,
                Map.of("chara", characterName, "riv", rivalName)
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        BattleHistoryDto battleResult = response.getBody();

        assertThat(battleResult.character()).isEqualTo(characterName);
        assertThat(battleResult.rival()).isEqualTo(rivalName);
        assertThat(battleResult.winner()).isNotBlank();
    }

    @Test
    void shouldReturnBadRequestWhenCharactersOfSameType() {
        String sameTypeChar1 = "Batman";
        String sameTypeChar2 = "Superman";

        ResponseEntity<String> response = restTemplate.getForEntity(
                BATTLE_ENDPOINT + "?character={char}&rival={riv}",
                String.class,
                Map.of("char", sameTypeChar1, "riv", sameTypeChar2)
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Characters of the same type cannot fight.");
    }

    @Test
    void shouldReturnBadRequestWhenCharacterNotFound() {
        String nonExistentCharacter = "NonExistentHero";

        ResponseEntity<String> response = restTemplate.getForEntity(
                BATTLE_ENDPOINT + "?character={char}&rival={riv}",
                String.class,
                Map.of("char", nonExistentCharacter, "riv", validRival)
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Character not found: " + nonExistentCharacter);
    }

    @Test
    void shouldPaginateBattleHistory() {
        // given
        int page = 0;
        int size = 2;

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                BATTLE_HISTORY_ENDPOINT + "?page={page}&size={size}",
                String.class,
                Map.of("page", page, "size", size)
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"content\"");
        assertThat(response.getBody()).contains("\"totalElements\"");
    }
}
