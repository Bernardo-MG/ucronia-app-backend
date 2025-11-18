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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberDtoMapper;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.MemberApi;
import com.bernardomg.ucronia.openapi.model.MemberPageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberStatusChangeDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Public member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MemberController implements MemberApi {

    /**
     * Public member service.
     */
    private final MemberService service;

    public MemberController(final MemberService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.MEMBERS)
    public MemberPageResponseDto getAllMembers(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort) {
        final Pagination   pagination;
        final Sorting      sorting;
        final Page<Member> members;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        members = service.getAll(pagination, sorting);

        return MemberDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.MEMBER)
    public MemberResponseDto getMemberByNumber(final Long number) {
        Optional<Member> member;

        member = service.getOne(number);

        return MemberDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MembersCaches.MEMBER_CONTACT, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Member caches
                    MembersCaches.MEMBER_CONTACTS }, allEntries = true) })
    public MemberResponseDto updateMemberStatus(@Valid final MemberStatusChangeDto memberStatusChangeDto) {
        // TODO Auto-generated method stub
        return null;
    }

}
