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

package com.bernardomg.association.library.author.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.author.adapter.outbound.rest.model.AuthorDtoMapper;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.usecase.service.AuthorService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.AuthorApi;
import com.bernardomg.ucronia.openapi.model.AuthorChangeDto;
import com.bernardomg.ucronia.openapi.model.AuthorCreationDto;
import com.bernardomg.ucronia.openapi.model.AuthorPageResponseDto;
import com.bernardomg.ucronia.openapi.model.AuthorResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Author REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class AuthorController implements AuthorApi {

    /**
     * Author service.
     */
    private final AuthorService service;

    public AuthorController(final AuthorService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.CREATE)
    public AuthorResponseDto createAuthor(@Valid final AuthorCreationDto authorCreationDto) {
        final Author author;

        author = service.create(new Author(-1L, authorCreationDto.getName()));

        return AuthorDtoMapper.toResponseDto(author);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.DELETE)
    public AuthorResponseDto deleteAuthor(final Long number) {
        final Author author;

        author = service.delete(number);

        return AuthorDtoMapper.toResponseDto(author);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.READ)
    public AuthorPageResponseDto getAllAuthors(@Min(0) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort) {
        final Pagination   pagination;
        final Sorting      sorting;
        final Page<Author> authors;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        authors = service.getAll(pagination, sorting);

        return AuthorDtoMapper.toResponseDto(authors);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.READ)
    public AuthorResponseDto getAuthorById(final Long number) {
        final Optional<Author> author;

        author = service.getOne(number);

        return AuthorDtoMapper.toResponseDto(author);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.CREATE)
    public AuthorResponseDto updateAuthor(final Long number, @Valid final AuthorChangeDto authorChangeDto) {
        final Author updated;
        final Author author;

        author = new Author(number, authorChangeDto.getName());
        updated = service.update(author);

        return AuthorDtoMapper.toResponseDto(updated);
    }

}
