package com.example.superheroes.controller;

import com.example.superheroes.model.BattleHistoryDto;
import com.example.superheroes.service.BattleCommandService;
import com.example.superheroes.service.BattleQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Battle", description = "Endpoints for managing battles")
@RequiredArgsConstructor
public class BattleController {
    private final BattleCommandService battleCommandService;
    private final BattleQueryService battleQueryService;

    @Operation(
            summary = "Battle between two characters",
            description = "Determines the winner based on the scores of the characters.",
            parameters = {
                    @Parameter(name = "character", description = "Name of the superhero initiating the battle", required = true),
                    @Parameter(name = "rival", description = "Name of the supervillain to battle against", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Battle completed successfully",
                            content = @Content(schema = @Schema(implementation = BattleHistoryDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input (e.g., characters of the same type cannot battle)",
                            content = @Content
                    )
            }
    )
    @GetMapping("/battle")
    public BattleHistoryDto battle(
            @RequestParam String character,
            @RequestParam String rival
    ) {
        return battleCommandService.battle(character, rival);
    }

    @Operation(
            summary = "Retrieve paginated battle history",
            description = "Returns a paginated list of all battles with their results.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (starting from 0)", required = true),
                    @Parameter(name = "size", description = "Number of records per page", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of battle history retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping("/battle_history")
    public Page<BattleHistoryDto> getBattleHistory(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return battleQueryService.getBattleHistory(page, size);
    }
}
