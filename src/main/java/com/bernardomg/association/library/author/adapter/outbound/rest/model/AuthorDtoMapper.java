
package com.bernardomg.association.library.author.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.AuthorDto;
import com.bernardomg.ucronia.openapi.model.AuthorPageResponseDto;
import com.bernardomg.ucronia.openapi.model.AuthorResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class AuthorDtoMapper {

    public static final AuthorResponseDto toResponseDto(final Author author) {
        return new AuthorResponseDto().content(toDto(author));
    }

    public static final AuthorResponseDto toResponseDto(final Optional<Author> author) {
        return new AuthorResponseDto().content(author.map(AuthorDtoMapper::toDto)
            .orElse(null));
    }

    public static final AuthorPageResponseDto toResponseDto(final Page<Author> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(AuthorDtoMapper::toDto)
            .toList());
        return new AuthorPageResponseDto().content(page.content()
            .stream()
            .map(AuthorDtoMapper::toDto)
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

    private static final AuthorDto toDto(final Author author) {
        return new AuthorDto().number(author.number())
            .name(author.name());
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

    private AuthorDtoMapper() {
        super();
    }

}
