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

package com.bernardomg.association.sponsor.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.sponsor.adapter.outbound.rest.model.SponsorDtoMapper;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.usecase.service.SponsorService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.SponsorApi;
import com.bernardomg.ucronia.openapi.model.SponsorCreationDto;
import com.bernardomg.ucronia.openapi.model.SponsorPageResponseDto;
import com.bernardomg.ucronia.openapi.model.SponsorPatchDto;
import com.bernardomg.ucronia.openapi.model.SponsorResponseDto;
import com.bernardomg.ucronia.openapi.model.SponsorUpdateDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Sponsor REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class SponsorController implements SponsorApi {

    private final SponsorService service;

    public SponsorController(final SponsorService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.CREATE)
    public SponsorResponseDto createSponsor(@Valid final SponsorCreationDto sponsorCreationDto) {
        final Sponsor sponsor;
        final Sponsor created;

        sponsor = SponsorDtoMapper.toDomain(sponsorCreationDto);
        created = service.create(sponsor);

        return SponsorDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.DELETE)
    public SponsorResponseDto deleteSponsor(final Long number) {
        final Sponsor sponsor;

        sponsor = service.delete(number);

        return SponsorDtoMapper.toResponseDto(sponsor);
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.READ)
    public SponsorPageResponseDto getAllSponsors(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(firstName|lastName|number)\\|(asc|desc)$") String> sort,
            @Valid final String name) {
        final Pagination    pagination;
        final Sorting       sorting;
        final Page<Sponsor> members;
        final SponsorFilter filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        filter = new SponsorFilter(name);

        members = service.getAll(filter, pagination, sorting);

        return SponsorDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.READ)
    public SponsorResponseDto getSponsorByNumber(final Long number) {
        Optional<Sponsor> member;

        member = service.getOne(number);

        return SponsorDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.UPDATE)
    public SponsorResponseDto patchSponsor(final Long number, @Valid final SponsorPatchDto sponsorPatchDto) {
        final Sponsor member;
        final Sponsor updated;

        member = SponsorDtoMapper.toDomain(number, sponsorPatchDto);
        updated = service.patch(member);

        return SponsorDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "SPONSOR", action = Actions.UPDATE)
    public SponsorResponseDto updateSponsor(final Long number, @Valid final SponsorUpdateDto sponsorUpdateDto) {
        final Sponsor member;
        final Sponsor updated;

        member = SponsorDtoMapper.toDomain(number, sponsorUpdateDto);
        updated = service.update(member);

        return SponsorDtoMapper.toResponseDto(updated);
    }

}
