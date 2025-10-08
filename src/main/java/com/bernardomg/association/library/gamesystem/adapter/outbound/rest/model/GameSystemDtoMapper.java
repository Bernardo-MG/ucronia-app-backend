
package com.bernardomg.association.library.gamesystem.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.GameSystemDto;
import com.bernardomg.ucronia.openapi.model.GameSystemPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GameSystemResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class GameSystemDtoMapper {

    public static final GameSystemResponseDto toResponseDto(final GameSystem gameSystem) {
        return new GameSystemResponseDto().content(toDto(gameSystem));
    }

    public static final GameSystemResponseDto toResponseDto(final Optional<GameSystem> gameSystem) {
        return new GameSystemResponseDto().content(gameSystem.map(GameSystemDtoMapper::toDto)
            .orElse(null));
    }

    public static final GameSystemPageResponseDto toResponseDto(final Page<GameSystem> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(GameSystemDtoMapper::toDto)
            .toList());
        return new GameSystemPageResponseDto().content(page.content()
            .stream()
            .map(GameSystemDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sortingResponse);
    }

    private static final GameSystemDto toDto(final GameSystem gameSystem) {
        return new GameSystemDto().number(gameSystem.number())
            .name(gameSystem.name());
    }

    private static final PropertyDto toDto(final Property property) {
        final DirectionEnum direction;

        if (property.direction() == Direction.ASC) {
            direction = DirectionEnum.ASC;
        } else {
            direction = DirectionEnum.DESC;
        }
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private GameSystemDtoMapper() {
        super();
    }

}
