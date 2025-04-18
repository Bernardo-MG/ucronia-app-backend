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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.book.adapter.outbound.cache.LibraryBookCaches;
import com.bernardomg.association.library.lending.adapter.outbound.rest.model.BookLent;
import com.bernardomg.association.library.lending.adapter.outbound.rest.model.BookReturned;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Book lending REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/lending")
@AllArgsConstructor
public class BookLendingController {

    /**
     * Book lending service.
     */
    private final BookLendingService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS, LibraryBookCaches.GAME_BOOKS,
            LibraryBookCaches.FICTION_BOOK, LibraryBookCaches.GAME_BOOK }, allEntries = true) })
    public BookLending lendBook(@Valid @RequestBody final BookLent lending) {
        return service.lendBook(lending.getBook(), lending.getPerson(), lending.getLendingDate());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.READ)
    // @Cacheable(cacheNames = LibraryLendingCaches.LENDINGS)
    public Iterable<BookLending> readAll(final Pagination pagination, final Sorting sorting) {
        // TODO: reapply cache
        return service.getAll(pagination, sorting);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_LENDING", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS, LibraryBookCaches.GAME_BOOKS,
            LibraryBookCaches.FICTION_BOOK, LibraryBookCaches.GAME_BOOK }, allEntries = true) })
    public BookLending returnBook(@Valid @RequestBody final BookReturned lending) {
        return service.returnBook(lending.getBook(), lending.getBorrower(), lending.getReturnDate());
    }

}
