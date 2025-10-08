
package com.bernardomg.association.library.booktype.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.BookTypeDto;
import com.bernardomg.ucronia.openapi.model.BookTypePageResponseDto;
import com.bernardomg.ucronia.openapi.model.BookTypeResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class BookTypeDtoMapper {

    public static final BookTypeResponseDto toResponseDto(final BookType bookType) {
        return new BookTypeResponseDto().content(toDto(bookType));
    }

    public static final BookTypeResponseDto toResponseDto(final Optional<BookType> bookType) {
        return new BookTypeResponseDto().content(bookType.map(BookTypeDtoMapper::toDto)
            .orElse(null));
    }

    public static final BookTypePageResponseDto toResponseDto(final Page<BookType> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(BookTypeDtoMapper::toDto)
            .toList());
        return new BookTypePageResponseDto().content(page.content()
            .stream()
            .map(BookTypeDtoMapper::toDto)
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

    private static final BookTypeDto toDto(final BookType bookType) {
        return new BookTypeDto().number(bookType.number())
            .name(bookType.name());
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

    private BookTypeDtoMapper() {
        super();
    }

}
