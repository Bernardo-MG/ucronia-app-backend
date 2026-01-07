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

package com.bernardomg.association.member.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.member.adapter.outbound.rest.model.MemberProfileDtoMapper;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.usecase.service.MemberProfileService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.MemberProfileApi;
import com.bernardomg.ucronia.openapi.model.MemberProfileChangeDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileCreationDto;
import com.bernardomg.ucronia.openapi.model.MemberProfilePageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberStatusDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * MemberProfile REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MemberProfileController implements MemberProfileApi {

    private final MemberProfileService service;

    public MemberProfileController(final MemberProfileService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.CREATE)
    public MemberProfileResponseDto
            createMemberProfile(@Valid final MemberProfileCreationDto memberProfileCreationDto) {
        final MemberProfile memberProfile;
        final MemberProfile created;

        memberProfile = MemberProfileDtoMapper.toDomain(memberProfileCreationDto);
        created = service.create(memberProfile);

        return MemberProfileDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.DELETE)
    public MemberProfileResponseDto deleteMemberProfile(final Long number) {
        final MemberProfile memberProfile;

        memberProfile = service.delete(number);

        return MemberProfileDtoMapper.toResponseDto(memberProfile);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public MemberProfilePageResponseDto getAllMemberProfiles(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(firstName|lastName|number)\\|(asc|desc)$") String> sort,
            @Valid final MemberStatusDto status, @Valid final String name) {
        final Pagination          pagination;
        final Sorting             sorting;
        final Page<MemberProfile> members;
        final MemberStatus        memberStatus;
        final MemberFilter        filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        if (status != null) {
            memberStatus = MemberStatus.valueOf(status.name());
        } else {
            memberStatus = null;
        }
        filter = new MemberFilter(memberStatus, name);

        members = service.getAll(filter, pagination, sorting);

        return MemberProfileDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public MemberProfileResponseDto getMemberProfileByNumber(final Long number) {
        Optional<MemberProfile> member;

        member = service.getOne(number);

        return MemberProfileDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.UPDATE)
    public MemberProfileResponseDto patchMemberProfile(final Long number,
            @Valid final MemberProfileChangeDto memberProfileChangeDto) {
        final MemberProfile member;
        final MemberProfile updated;

        member = MemberProfileDtoMapper.toDomain(number, memberProfileChangeDto);
        updated = service.patch(member);

        return MemberProfileDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.UPDATE)
    public MemberProfileResponseDto updateMemberProfile(final Long number,
            @Valid final MemberProfileChangeDto memberProfileChangeDto) {
        final MemberProfile member;
        final MemberProfile updated;

        member = MemberProfileDtoMapper.toDomain(number, memberProfileChangeDto);
        updated = service.update(member);

        return MemberProfileDtoMapper.toResponseDto(updated);
    }

}
