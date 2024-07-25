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

package com.bernardomg.association.library.adapter.outbound.rest.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
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

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.library.adapter.outbound.cache.LibraryCaches;
import com.bernardomg.association.library.adapter.outbound.rest.model.BookCreation;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.usecase.service.BookService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Book REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/book")
@AllArgsConstructor
public class BookController {

    /**
     * Book service.
     */
    private final BookService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = LibraryCaches.BOOK, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { LibraryCaches.BOOKS }, allEntries = true) })
    public Book create(@Valid @RequestBody final BookCreation request) {
        final Book book;

        // Book
        book = toDomain(request, 0);

        return service.create(book);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryCaches.BOOK }),
            @CacheEvict(cacheNames = { LibraryCaches.BOOKS }, allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryCaches.BOOKS)
    public Iterable<Book> readAll(final Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryCaches.BOOK)
    public Book readOne(@PathVariable("number") final long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = LibraryCaches.BOOK, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { LibraryCaches.BOOKS }, allEntries = true) })
    public Book update(@PathVariable("number") final long number, @Valid @RequestBody final BookCreation request) {
        final Book book;

        // Book
        book = toDomain(request, number);

        return service.update(number, book);
    }

    private final Book toDomain(final BookCreation request, final long number) {
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final BookType              bookType;
        final GameSystem            gameSystem;
        final Collection<Donor>     donors;

        // Authors
        if (request.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = request.getAuthors()
                .stream()
                .map(a -> Author.builder()
                    .withName(a.getName())
                    .build())
                .toList();
        }

        // Publishers
        if (request.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = request.getPublishers()
                .stream()
                .map(p -> Publisher.builder()
                    .withName(p.getName())
                    .build())
                .toList();
        }

        // Book type
        if (request.getBookType() == null) {
            bookType = BookType.builder()
                .build();
        } else {
            bookType = BookType.builder()
                .withName(request.getBookType()
                    .getName())
                .build();
        }

        // Game system
        if (request.getGameSystem() == null) {
            gameSystem = GameSystem.builder()
                .build();
        } else {
            gameSystem = GameSystem.builder()
                .withName(request.getGameSystem()
                    .getName())
                .build();
        }

        // Donor
        if (request.getDonors() == null) {
            donors = List.of();
        } else {
            donors = request.getDonors()
                .stream()
                .map(d -> Donor.builder()
                    .withNumber(d.getNumber())
                    .build())
                .toList();
        }

        // Book
        return Book.builder()
            .withTitle(request.getTitle())
            .withIsbn(request.getIsbn())
            .withLanguage(request.getLanguage())
            .withAuthors(authors)
            .withPublishers(publishers)
            .withBookType(bookType)
            .withGameSystem(gameSystem)
            .withDonors(donors)
            .withNumber(number)
            .build();
    }

}
