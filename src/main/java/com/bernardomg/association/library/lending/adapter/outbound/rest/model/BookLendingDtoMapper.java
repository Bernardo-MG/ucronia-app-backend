
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.BookLendingDto;
import com.bernardomg.ucronia.openapi.model.BookLendingPageResponseDto;
import com.bernardomg.ucronia.openapi.model.BookLendingResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.MinimalContactDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class BookLendingDtoMapper {

    public static final BookLendingResponseDto toResponseDto(final BookLending author) {
        return new BookLendingResponseDto().content(toDto(author));
    }

    public static final BookLendingResponseDto toResponseDto(final Optional<BookLending> author) {
        return new BookLendingResponseDto().content(author.map(BookLendingDtoMapper::toDto)
            .orElse(null));
    }

    public static final BookLendingPageResponseDto toResponseDto(final Page<BookLending> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(BookLendingDtoMapper::toDto)
            .toList());
        return new BookLendingPageResponseDto().content(page.content()
            .stream()
            .map(BookLendingDtoMapper::toDto)
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

    private static final BookLendingDto toDto(final BookLending lending) {
        final ContactNameDto    contactName;
        final MinimalContactDto borrower;

        contactName = new ContactNameDto().firstName(lending.borrower()
            .name()
            .firstName())
            .lastName(lending.borrower()
                .name()
                .lastName())
            .fullName(lending.borrower()
                .name()
                .fullName());
        borrower = new MinimalContactDto().name(contactName)
            .number(lending.borrower()
                .number());
        return new BookLendingDto().book(lending.book()
            .number())
            .borrower(borrower)
            .lendingDate(lending.lendingDate())
            .returnDate(lending.returnDate());
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

    private BookLendingDtoMapper() {
        super();
    }

}
