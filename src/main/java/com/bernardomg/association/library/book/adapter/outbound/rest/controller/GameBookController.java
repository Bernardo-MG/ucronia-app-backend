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

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.book.adapter.outbound.cache.LibraryBookCaches;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookDtoMapper;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.usecase.service.GameBookService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.GameBookApi;
import com.bernardomg.ucronia.openapi.model.BookCreationDto;
import com.bernardomg.ucronia.openapi.model.GameBookPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GameBookResponseDto;
import com.bernardomg.ucronia.openapi.model.GameBookUpdateDto;

import jakarta.validation.Valid;

/**
 * Game book REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/library/book/game")
public class GameBookController implements GameBookApi {

    /**
     * Game book service.
     */
    private final GameBookService service;

    public GameBookController(final GameBookService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = LibraryBookCaches.GAME_BOOK, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = { LibraryBookCaches.GAME_BOOKS }, allEntries = true) })
    public GameBookResponseDto createGameBook(@Valid final BookCreationDto bookCreationDto) {
        final GameBook gameBook;

        gameBook = service.create(BookDtoMapper.toGameDomain(bookCreationDto));

        return BookDtoMapper.toResponseDto(gameBook);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { LibraryBookCaches.GAME_BOOK }),
            @CacheEvict(cacheNames = { LibraryBookCaches.GAME_BOOKS }, allEntries = true) })
    public GameBookResponseDto deleteGameBook(final Long number) {
        final GameBook gameBook;

        gameBook = service.delete(number);

        return BookDtoMapper.toResponseDto(gameBook);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryBookCaches.GAME_BOOKS)
    public GameBookPageResponseDto getAllGameBooks(@Valid final Integer page, @Valid final Integer size,
            @Valid final List<String> sort) {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<GameBook> gameBooks;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        gameBooks = service.getAll(pagination, sorting);

        return BookDtoMapper.toGameResponseDto(gameBooks);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.READ)
    @Cacheable(cacheNames = LibraryBookCaches.GAME_BOOK)
    public GameBookResponseDto getGameBookById(final Long number) {
        final Optional<GameBook> gameBook;

        gameBook = service.getOne(number);

        return BookDtoMapper.toGameResponseDto(gameBook);
    }

    @Override
    @RequireResourceAccess(resource = "LIBRARY_BOOK", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = LibraryBookCaches.GAME_BOOK, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = { LibraryBookCaches.GAME_BOOKS }, allEntries = true) })
    public GameBookResponseDto updateGameBook(final Long number, @Valid final GameBookUpdateDto gameBookUpdateDto) {
        final GameBook updated;
        final GameBook gameBook;

        gameBook = BookDtoMapper.toDomain(gameBookUpdateDto, number);
        updated = service.update(gameBook);

        return BookDtoMapper.toResponseDto(updated);
    }

}
