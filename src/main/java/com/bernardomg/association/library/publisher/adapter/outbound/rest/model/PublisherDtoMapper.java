
package com.bernardomg.association.library.publisher.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.PublisherDto;
import com.bernardomg.ucronia.openapi.model.PublisherPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PublisherResponseDto;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class PublisherDtoMapper {

    public static final PublisherResponseDto toResponseDto(final Optional<Publisher> gameSystem) {
        return new PublisherResponseDto().content(gameSystem.map(PublisherDtoMapper::toDto)
            .orElse(null));
    }

    public static final PublisherPageResponseDto toResponseDto(final Page<Publisher> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(PublisherDtoMapper::toDto)
            .toList());
        return new PublisherPageResponseDto().content(page.content()
            .stream()
            .map(PublisherDtoMapper::toDto)
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

    public static final PublisherResponseDto toResponseDto(final Publisher gameSystem) {
        return new PublisherResponseDto().content(toDto(gameSystem));
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

    private static final PublisherDto toDto(final Publisher gameSystem) {
        return new PublisherDto().number(gameSystem.number())
            .name(gameSystem.name());
    }

    private PublisherDtoMapper() {
        super();
    }

}
