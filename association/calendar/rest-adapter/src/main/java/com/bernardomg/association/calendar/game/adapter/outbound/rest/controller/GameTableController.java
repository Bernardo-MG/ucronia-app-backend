/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableCreationDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTablePageResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableUpdateDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.model.GameTableDtoMapper;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.usecase.service.GameTableService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Game table REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class GameTableController implements GameTableApi {

    /**
     * Game table service.
     */
    private final GameTableService service;

    public GameTableController(final GameTableService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "GAME_TABLE", action = Actions.CREATE)
    public GameTableResponseDto createGameTable(@Valid final GameTableCreationDto gameTableCreationDto) {
        final GameTable gameTable;
        final GameTable toCreate;

        toCreate = GameTableDtoMapper.toDomain(gameTableCreationDto);
        gameTable = service.create(toCreate);

        return GameTableDtoMapper.toResponseDto(gameTable);
    }

    @Override
    @RequireResourceAuthorization(resource = "GAME_TABLE", action = Actions.DELETE)
    public GameTableResponseDto deleteGameTable(final Long number) {
        final GameTable gameTable;

        gameTable = service.delete(number);

        return GameTableDtoMapper.toResponseDto(gameTable);
    }

    @Override
    @RequireResourceAuthorization(resource = "GAME_TABLE", action = Actions.READ)
    public GameTablePageResponseDto getAllGameTables(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(number|name|description)\\|(asc|desc)$") String> sort) {
        final Pagination      pagination;
        final Sorting         sorting;
        final Page<GameTable> gameTables;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        gameTables = service.getAll(pagination, sorting);

        return GameTableDtoMapper.toResponseDto(gameTables);
    }

    @Override
    @RequireResourceAuthorization(resource = "GAME_TABLE", action = Actions.READ)
    public GameTableResponseDto getOneGameTable(final Long number) {
        final Optional<GameTable> gameTable;

        gameTable = service.getOne(number);

        return GameTableDtoMapper.toResponseDto(gameTable);
    }

    @Override
    @RequireResourceAuthorization(resource = "GAME_TABLE", action = Actions.UPDATE)
    public GameTableResponseDto updateGameTable(final Long number, @Valid final GameTableUpdateDto gameTableUpdateDto) {
        final GameTable gameTable;
        final GameTable updated;

        gameTable = GameTableDtoMapper.toDomain(number, gameTableUpdateDto);
        updated = service.update(gameTable);
        return GameTableDtoMapper.toResponseDto(updated);
    }

}
