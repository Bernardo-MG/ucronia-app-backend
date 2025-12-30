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

package com.bernardomg.association.library.gamesystem.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.gamesystem.adapter.outbound.rest.model.GameSystemDtoMapper;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.GameSystemApi;
import com.bernardomg.ucronia.openapi.model.GameSystemChangeDto;
import com.bernardomg.ucronia.openapi.model.GameSystemCreationDto;
import com.bernardomg.ucronia.openapi.model.GameSystemPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GameSystemResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Author REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class GameSystemController implements GameSystemApi {

    /**
     * Author service.
     */
    private final GameSystemService service;

    public GameSystemController(final GameSystemService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_GAME_SYSTEM", action = Actions.CREATE)
    public GameSystemResponseDto createGameSystem(@Valid final GameSystemCreationDto gameSystemCreationDto) {
        final GameSystem gameSystem;

        gameSystem = service.create(new GameSystem(-1L, gameSystemCreationDto.getName()));

        return GameSystemDtoMapper.toResponseDto(gameSystem);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_GAME_SYSTEM", action = Actions.DELETE)
    public GameSystemResponseDto deleteGameSystem(final Long number) {
        final GameSystem gameSystem;

        gameSystem = service.delete(number);

        return GameSystemDtoMapper.toResponseDto(gameSystem);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_GAME_SYSTEM", action = Actions.READ)
    public GameSystemPageResponseDto getAllGameSystems(@Min(0) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Pagination       pagination;
        final Sorting          sorting;
        final Page<GameSystem> gameSystems;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        gameSystems = service.getAll(pagination, sorting);

        return GameSystemDtoMapper.toResponseDto(gameSystems);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_GAME_SYSTEM", action = Actions.READ)
    public GameSystemResponseDto getGameSystemById(final Long number) {
        final Optional<GameSystem> gameSystem;

        gameSystem = service.getOne(number);

        return GameSystemDtoMapper.toResponseDto(gameSystem);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.UPDATE)
    public GameSystemResponseDto updateGameSystem(final Long number,
            @Valid final GameSystemChangeDto gameSystemChangeDto) {
        final GameSystem updated;
        final GameSystem gameSystem;

        gameSystem = new GameSystem(number, gameSystemChangeDto.getName());
        updated = service.update(gameSystem);

        return GameSystemDtoMapper.toResponseDto(updated);
    }

}
