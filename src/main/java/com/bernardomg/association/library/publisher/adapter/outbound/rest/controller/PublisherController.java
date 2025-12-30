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
 * PUBLISHERS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.publisher.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.library.publisher.adapter.outbound.rest.model.PublisherDtoMapper;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.usecase.service.PublisherService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.PublisherApi;
import com.bernardomg.ucronia.openapi.model.PublisherChangeDto;
import com.bernardomg.ucronia.openapi.model.PublisherCreationDto;
import com.bernardomg.ucronia.openapi.model.PublisherPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PublisherResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Publisher REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class PublisherController implements PublisherApi {

    /**
     * Publisher service.
     */
    private final PublisherService service;

    public PublisherController(final PublisherService service) {
        super();
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAuthorization(resource = "LIBRARY_PUBLISHER", action = Actions.CREATE)
    public PublisherResponseDto createPublisher(@Valid final PublisherCreationDto publisherCreationDto) {
        final Publisher publisher;

        publisher = service.create(new Publisher(-1L, publisherCreationDto.getName()));

        return PublisherDtoMapper.toResponseDto(publisher);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_PUBLISHER", action = Actions.DELETE)
    public PublisherResponseDto deletePublisher(final Long number) {
        final Publisher publisher;

        publisher = service.delete(number);

        return PublisherDtoMapper.toResponseDto(publisher);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_PUBLISHER", action = Actions.READ)
    public PublisherPageResponseDto getAllPublishers(@Min(0) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Pagination      pagination;
        final Sorting         sorting;
        final Page<Publisher> publishers;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        publishers = service.getAll(pagination, sorting);

        return PublisherDtoMapper.toResponseDto(publishers);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_PUBLISHER", action = Actions.READ)
    public PublisherResponseDto getPublisherById(final Long number) {
        final Optional<Publisher> publisher;

        publisher = service.getOne(number);

        return PublisherDtoMapper.toResponseDto(publisher);
    }

    @Override
    @RequireResourceAuthorization(resource = "LIBRARY_AUTHOR", action = Actions.UPDATE)
    public PublisherResponseDto updatePublisher(final Long number, @Valid final PublisherChangeDto publisherChangeDto) {
        final Publisher updated;
        final Publisher publisher;

        publisher = new Publisher(number, publisherChangeDto.getName());
        updated = service.update(publisher);

        return PublisherDtoMapper.toResponseDto(updated);
    }

}
