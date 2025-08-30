/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.lending.adapter.outbound.rest.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.book.adapter.outbound.cache.LibraryBookCaches;
import com.bernardomg.association.library.lending.adapter.outbound.rest.model.BookLendingDtoMapper;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.BookLendingApi;
import com.bernardomg.ucronia.openapi.model.BookLendingPageResponseDto;
import com.bernardomg.ucronia.openapi.model.BookLendingResponseDto;
import com.bernardomg.ucronia.openapi.model.BookLentDto;
import com.bernardomg.ucronia.openapi.model.BookReturnedDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Book lending REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class BookLendingController implements BookLendingApi {

    /**
     * Book lending service.
     */
    private final BookLendingService service;

    public BookLendingController(final BookLendingService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.READ)
    // @Cacheable(cacheNames = LibraryLendingCaches.LENDINGS)
    public BookLendingPageResponseDto getAllBookLendings(@Min(0) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Page<BookLending> lendings;
        final Pagination        pagination;
        final Sorting           sorting;
        // TODO: reapply cache

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        lendings = service.getAll(pagination, sorting);

        return BookLendingDtoMapper.toResponseDto(lendings);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS, LibraryBookCaches.GAME_BOOKS,
            LibraryBookCaches.FICTION_BOOK, LibraryBookCaches.GAME_BOOK }, allEntries = true) })
    public BookLendingResponseDto lendBook(@Valid final BookLentDto bookLentDto) {
        final BookLending lending;

        lending = service.lendBook(bookLentDto.getBook(), bookLentDto.getBorrower(),
            bookLentDto.getLendingDate());

        return BookLendingDtoMapper.toResponseDto(lending);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS, LibraryBookCaches.GAME_BOOKS,
            LibraryBookCaches.FICTION_BOOK, LibraryBookCaches.GAME_BOOK }, allEntries = true) })
    public BookLendingResponseDto returnBook(@Valid final BookReturnedDto bookReturnedDto) {
        final BookLending lending;

        lending = service.returnBook(bookReturnedDto.getBook(), bookReturnedDto.getBorrower(),
            bookReturnedDto.getReturnDate());

        return BookLendingDtoMapper.toResponseDto(lending);
    }

}
