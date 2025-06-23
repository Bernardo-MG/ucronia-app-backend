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

package com.bernardomg.association.library.book.adapter.outbound.rest.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.outbound.cache.LibraryBookCaches;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Game book REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/book/fiction")
@AllArgsConstructor
public class FictionBookController {

    /**
     * Game book service.
     */
    private final FictionBookService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = LibraryBookCaches.FICTION_BOOK, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS }, allEntries = true) })
    public FictionBook create(@Valid @RequestBody final FictionBookCreation request) {
        final FictionBook book;

        // Book
        book = toDomain(request, 0);

        return service.create(book);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOK }),
            @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS }, allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryBookCaches.FICTION_BOOKS)
    public Iterable<FictionBook> readAll(final Pagination pagination, final Sorting sorting) {
        return service.getAll(pagination, sorting);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryBookCaches.FICTION_BOOK)
    public FictionBook readOne(@PathVariable("number") final long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = LibraryBookCaches.FICTION_BOOK, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { LibraryBookCaches.FICTION_BOOKS }, allEntries = true) })
    public FictionBook update(@PathVariable("number") final long number,
            @Valid @RequestBody final FictionBookUpdate request) {
        final FictionBook book;

        // Book
        book = toDomain(request, number);

        return service.update(number, book);
    }

    private final FictionBook toDomain(final FictionBookCreation request, final long number) {
        final Title  title;
        final String supertitle;
        final String subtitle;

        if (request.title()
            .supertitle() == null) {
            supertitle = "";
        } else {
            supertitle = request.title()
                .supertitle();
        }
        if (request.title()
            .subtitle() == null) {
            subtitle = "";
        } else {
            subtitle = request.title()
                .subtitle();
        }

        title = new Title(supertitle, request.title()
            .title(), subtitle);
        return new FictionBook(number, title, request.isbn(), request.language(), null, false, List.of(), List.of(),
            List.of(), Optional.empty());
    }

    private final FictionBook toDomain(final FictionBookUpdate request, final long number) {
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;
        final Title                 title;
        final String                supertitle;
        final String                subtitle;
        final LocalDate             donationDate;
        final Optional<Donation>    donation;

        // Authors
        if (request.authors() == null) {
            authors = List.of();
        } else {
            authors = request.authors()
                .stream()
                .map(a -> new Author(a.number(), ""))
                .toList();
        }

        // Publishers
        if (request.publishers() == null) {
            publishers = List.of();
        } else {
            publishers = request.publishers()
                .stream()
                .map(p -> new Publisher(p.number(), ""))
                .toList();
        }

        // Donation
        if (request.donation() == null) {
            donation = Optional.empty();
        } else {
            if (request.donation()
                .donors() == null) {
                donors = List.of();
            } else {
                donors = request.donation()
                    .donors()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(d -> new Donor(d.number(), new PersonName("", "")))
                    .toList();
            }
            if (request.donation()
                .date() == null) {
                donationDate = null;
            } else {
                donationDate = request.donation()
                    .date();
            }
            if ((donationDate == null) && (donors.isEmpty())) {
                donation = Optional.empty();
            } else {
                donation = Optional.of(new Donation(donationDate, donors));
            }
        }

        // Title
        if (request.title()
            .supertitle() == null) {
            supertitle = "";
        } else {
            supertitle = request.title()
                .supertitle();
        }
        if (request.title()
            .subtitle() == null) {
            subtitle = "";
        } else {
            subtitle = request.title()
                .subtitle();
        }
        title = new Title(supertitle, request.title()
            .title(), subtitle);
        return new FictionBook(number, title, request.isbn(), request.language(), request.publishDate(), false, authors,
            List.of(), publishers, donation);
    }

}
