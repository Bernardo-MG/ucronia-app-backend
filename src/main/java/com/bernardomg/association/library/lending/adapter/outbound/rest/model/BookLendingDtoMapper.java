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

package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.BookLendingBookDto;
import com.bernardomg.ucronia.openapi.model.BookLendingDto;
import com.bernardomg.ucronia.openapi.model.BookLendingPageResponseDto;
import com.bernardomg.ucronia.openapi.model.BookLendingResponseDto;
import com.bernardomg.ucronia.openapi.model.BookTitleDto;
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
        return new BookLendingDto().book(toDto(lending.book()))
            .borrower(borrower)
            .lendingDate(lending.lendingDate())
            .returnDate(lending.returnDate())
            .days(lending.getDays());
    }

    private static final BookLendingBookDto toDto(final LentBook book) {
        final BookTitleDto title;

        title = new BookTitleDto().supertitle(book.title()
            .supertitle())
            .title(book.title()
                .title())
            .subtitle(book.title()
                .subtitle())
            .fullTitle(book.title()
                .fullTitle());
        return new BookLendingBookDto().number(book.number())
            .title(title);
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
