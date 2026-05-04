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

import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberCreationDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberPageResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberStatusDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberUpdateDto;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberDtoMapper;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MemberController implements MemberApi {

    private final MemberService service;

    public MemberController(final MemberService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.CREATE)
    public MemberResponseDto createMember(@Valid final MemberCreationDto MemberCreationDto) {
        final Member Member;
        final Member created;

        Member = MemberDtoMapper.toDomain(MemberCreationDto);
        created = service.create(Member);

        return MemberDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.DELETE)
    public MemberResponseDto deleteMember(final Long number) {
        final Member Member;

        Member = service.delete(number);

        return MemberDtoMapper.toResponseDto(Member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public MemberPageResponseDto getAllMembers(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(firstName|lastName|number)\\|(asc|desc)$") String> sort,
            @Valid final MemberStatusDto status, @Valid final String name) {
        final Pagination   pagination;
        final Sorting      sorting;
        final Page<Member> members;
        final MemberStatus memberStatus;
        final MemberFilter filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        if (status != null) {
            memberStatus = MemberStatus.valueOf(status.name());
        } else {
            memberStatus = null;
        }
        filter = new MemberFilter(memberStatus, name);

        members = service.getAll(filter, pagination, sorting);

        return MemberDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public MemberResponseDto getMemberByNumber(final Long number) {
        Optional<Member> member;

        member = service.getOne(number);

        return MemberDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.UPDATE)
    public MemberResponseDto patchMember(final Long number, @Valid final MemberUpdateDto MemberUpdateDto) {
        final Member member;
        final Member updated;

        member = MemberDtoMapper.toDomain(number, MemberUpdateDto);
        updated = service.patch(member);

        return MemberDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.UPDATE)
    public MemberResponseDto updateMember(final Long number, @Valid final MemberUpdateDto MemberUpdateDto) {
        final Member member;
        final Member updated;

        member = MemberDtoMapper.toDomain(number, MemberUpdateDto);
        updated = service.update(member);

        return MemberDtoMapper.toResponseDto(updated);
    }

}
