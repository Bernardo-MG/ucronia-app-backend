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

package com.bernardomg.association.library.book.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookDtoMapper;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FictionBookApi;
import com.bernardomg.ucronia.openapi.model.BookCreationDto;
import com.bernardomg.ucronia.openapi.model.FictionBookPageResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookUpdateDto;

import jakarta.validation.Valid;

/**
 * Game book REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class FictionBookController implements FictionBookApi {

    /**
     * Game book service.
     */
    private final FictionBookService service;

    public FictionBookController(final FictionBookService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_BOOK", action = Actions.CREATE)
    public FictionBookResponseDto createFictionBook(@Valid final BookCreationDto bookCreationDto) {
        final FictionBook fictionBook;

        fictionBook = service.create(BookDtoMapper.toFictionDomain(bookCreationDto));

        return BookDtoMapper.toResponseDto(fictionBook);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_BOOK", action = Actions.DELETE)
    public FictionBookResponseDto deleteFictionBook(final Long number) {
        final FictionBook fictionBook;

        fictionBook = service.delete(number);

        return BookDtoMapper.toResponseDto(fictionBook);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_BOOK", action = Actions.READ)
    public FictionBookPageResponseDto getAllFictionBooks(@Valid final Integer page, @Valid final Integer size,
            @Valid final List<String> sort) {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<FictionBook> fictionBooks;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        fictionBooks = service.getAll(pagination, sorting);

        return BookDtoMapper.toFictionResponseDto(fictionBooks);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_BOOK", action = Actions.READ)
    public FictionBookResponseDto getFictionBookById(final Long number) {
        final Optional<FictionBook> fictionBook;

        fictionBook = service.getOne(number);

        return BookDtoMapper.toFictionResponseDto(fictionBook);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_BOOK", action = Actions.UPDATE)
    public FictionBookResponseDto updateFictionBook(final Long number,
            @Valid final FictionBookUpdateDto fictionBookUpdateDto) {
        final FictionBook updated;
        final FictionBook fictionBook;

        fictionBook = BookDtoMapper.toDomain(fictionBookUpdateDto, number);
        updated = service.update(fictionBook);

        return BookDtoMapper.toResponseDto(updated);
    }

}
