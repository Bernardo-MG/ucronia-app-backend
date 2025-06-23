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

package com.bernardomg.association.library.author.adapter.outbound.rest.controller;

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

import com.bernardomg.association.library.author.adapter.outbound.cache.LibraryAuthorCaches;
import com.bernardomg.association.library.author.adapter.outbound.rest.model.AuthorChange;
import com.bernardomg.association.library.author.adapter.outbound.rest.model.AuthorCreation;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.usecase.service.AuthorService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;

/**
 * Author REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/author")
public class AuthorController {

    /**
     * Author service.
     */
    private final AuthorService service;

    public AuthorController(final AuthorService service) {
        super();
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "LIBRARY_AUTHOR", action = Actions.CREATE)
    @Caching(evict = {
            @CacheEvict(cacheNames = { LibraryAuthorCaches.AUTHORS, LibraryAuthorCaches.AUTHOR }, allEntries = true) })
    public Author create(@Valid @RequestBody final AuthorCreation request) {
        final Author author;

        author = new Author(-1L, request.name());
        return service.create(author);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_AUTHOR", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryAuthorCaches.AUTHOR }),
            @CacheEvict(cacheNames = { LibraryAuthorCaches.AUTHORS }, allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_AUTHOR", action = Actions.READ)
    @Cacheable(cacheNames = LibraryAuthorCaches.AUTHORS)
    public Iterable<Author> readAll(final Pagination pagination, final Sorting sorting) {
        return service.getAll(pagination, sorting);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_AUTHOR", action = Actions.READ)
    @Cacheable(cacheNames = LibraryAuthorCaches.AUTHOR)
    public Author readOne(@PathVariable("number") final long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_AUTHOR", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = LibraryAuthorCaches.AUTHOR, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { LibraryAuthorCaches.AUTHORS }, allEntries = true) })
    public Author update(@PathVariable("number") final long number, @Valid @RequestBody final AuthorChange change) {
        final Author author;

        author = new Author(number, change.name());
        return service.update(author);
    }

}
