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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.adapter.outbound.rest.model.GameSystemCreation;
import com.bernardomg.association.library.cache.LibraryCaches;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.usecase.service.GameSystemService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Author REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/gameSystem")
@AllArgsConstructor
public class GameSystemController {

    /**
     * Author service.
     */
    private final GameSystemService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "LIBRARY_GAME_SYSTEM", action = Actions.CREATE)
    @Caching(evict = {
            @CacheEvict(cacheNames = { LibraryCaches.GAME_SYSTEMS, LibraryCaches.GAME_SYSTEM }, allEntries = true) })
    public GameSystem create(@Valid @RequestBody final GameSystemCreation request) {
        final GameSystem author;

        author = GameSystem.builder()
            .withName(request.getName())
            .build();
        return service.create(author);
    }

    @DeleteMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_GAME_SYSTEM", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryCaches.GAME_SYSTEM }),
            @CacheEvict(cacheNames = { LibraryCaches.GAME_SYSTEMS }, allEntries = true) })
    public void delete(@PathVariable("name") final String name) {
        service.delete(name);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_GAME_SYSTEM", action = Actions.READ)
    @Cacheable(cacheNames = LibraryCaches.GAME_SYSTEMS)
    public Iterable<GameSystem> readAll(final Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "LIBRARY_GAME_SYSTEM", action = Actions.READ)
    @Cacheable(cacheNames = LibraryCaches.GAME_SYSTEM)
    public GameSystem readOne(@PathVariable("name") final String name) {
        return service.getOne(name)
            .orElse(null);
    }

}
