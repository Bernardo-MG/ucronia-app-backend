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

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberDtoMapper;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.usecase.service.PublicMemberService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.PublicMemberApi;
import com.bernardomg.ucronia.openapi.model.PublicMemberPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PublicMemberResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Public member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class PublicMemberController implements PublicMemberApi {

    /**
     * Public member service.
     */
    private final PublicMemberService service;

    public PublicMemberController(final PublicMemberService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "PUBLIC_MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.PUBLIC_MEMBERS)
    public PublicMemberPageResponseDto getAllPublicMembers(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Pagination         pagination;
        final Sorting            sorting;
        final Page<PublicMember> members;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        members = service.getAll(pagination, sorting);

        return MemberDtoMapper.toPublicResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "PUBLIC_MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.PUBLIC_MEMBER)
    public PublicMemberResponseDto getMemberByNumber(final Long number) {
        Optional<PublicMember> member;

        member = service.getOne(number);

        return MemberDtoMapper.toResponseDto(member);
    }

}
