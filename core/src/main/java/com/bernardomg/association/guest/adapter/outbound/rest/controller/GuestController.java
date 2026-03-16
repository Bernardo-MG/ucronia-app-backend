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

package com.bernardomg.association.guest.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.guest.adapter.outbound.rest.model.GuestDtoMapper;
import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.usecase.service.GuestService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.GuestApi;
import com.bernardomg.ucronia.openapi.model.GuestCreationDto;
import com.bernardomg.ucronia.openapi.model.GuestPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GuestPatchDto;
import com.bernardomg.ucronia.openapi.model.GuestResponseDto;
import com.bernardomg.ucronia.openapi.model.GuestUpdateDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Guest REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class GuestController implements GuestApi {

    private final GuestService service;

    public GuestController(final GuestService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.CREATE)
    public GuestResponseDto createGuest(@Valid final GuestCreationDto guestCreationDto) {
        final Guest guest;
        final Guest created;

        guest = GuestDtoMapper.toDomain(guestCreationDto);
        created = service.create(guest);

        return GuestDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.DELETE)
    public GuestResponseDto deleteGuest(final Long number) {
        final Guest guest;

        guest = service.delete(number);

        return GuestDtoMapper.toResponseDto(guest);
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.READ)
    public GuestPageResponseDto getAllGuests(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(firstName|lastName|number)\\|(asc|desc)$") String> sort,
            @Valid final String name) {
        final Pagination  pagination;
        final Sorting     sorting;
        final Page<Guest> members;
        final GuestFilter filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        filter = new GuestFilter(name);

        members = service.getAll(filter, pagination, sorting);

        return GuestDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.READ)
    public GuestResponseDto getGuestByNumber(final Long number) {
        Optional<Guest> member;

        member = service.getOne(number);

        return GuestDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.UPDATE)
    public GuestResponseDto patchGuest(final Long number, @Valid final GuestPatchDto guestUpdateDto) {
        final Guest member;
        final Guest updated;

        member = GuestDtoMapper.toDomain(number, guestUpdateDto);
        updated = service.patch(member);

        return GuestDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.UPDATE)
    public GuestResponseDto updateGuest(final Long number, @Valid final GuestUpdateDto guestUpdateDto) {
        final Guest member;
        final Guest updated;

        member = GuestDtoMapper.toDomain(number, guestUpdateDto);
        updated = service.update(member);

        return GuestDtoMapper.toResponseDto(updated);
    }

}
