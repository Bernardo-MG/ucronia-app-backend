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

import com.bernardomg.association.member.adapter.outbound.cache.MemberContactsCaches;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberContactDtoMapper;
import com.bernardomg.association.member.domain.filter.MemberQuery;
import com.bernardomg.association.member.domain.filter.MemberQuery.MemberFilterStatus;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.usecase.service.MemberContactService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.MemberContactApi;
import com.bernardomg.ucronia.openapi.model.MemberContactChangeDto;
import com.bernardomg.ucronia.openapi.model.MemberContactCreationDto;
import com.bernardomg.ucronia.openapi.model.MemberContactPageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberContactResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberStatusDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MemberContactController implements MemberContactApi {

    /**
     * Member service.
     */
    private final MemberContactService service;

    public MemberContactController(final MemberContactService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = MemberContactsCaches.MEMBER, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Member caches
                    MemberContactsCaches.MEMBERS }, allEntries = true) })
    public MemberContactResponseDto createMemberContact(@Valid final MemberContactCreationDto memberCreationDto) {
        final MemberContact member;
        final MemberContact created;

        member = MemberContactDtoMapper.toDomain(memberCreationDto);
        created = service.create(member);

        return MemberContactDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { MemberContactsCaches.MEMBER }), @CacheEvict(cacheNames = {
            // Member caches
            MemberContactsCaches.MEMBERS }, allEntries = true) })
    public MemberContactResponseDto deleteMemberContact(final Long number) {
        final MemberContact member;

        member = service.delete(number);

        return MemberContactDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MemberContactsCaches.MEMBERS)
    public MemberContactPageResponseDto getAllMemberContacts(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort, @Valid final MemberStatusDto status,
            @Valid final String name) {
        final Page<MemberContact> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilterStatus  memberStatus;
        final MemberQuery         filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        if (status != null) {
            memberStatus = MemberFilterStatus.valueOf(status.name());
        } else {
            memberStatus = null;
        }
        filter = new MemberQuery(memberStatus, name);
        members = service.getAll(filter, pagination, sorting);

        return MemberContactDtoMapper.toResponseDto(members);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MemberContactsCaches.MEMBER)
    public MemberContactResponseDto getMemberContactByNumber(final Long number) {
        Optional<MemberContact> member;

        member = service.getOne(number);

        return MemberContactDtoMapper.toResponseDto(member);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MemberContactsCaches.MEMBER, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Member caches
                    MemberContactsCaches.MEMBERS }, allEntries = true) })
    public MemberContactResponseDto patchMemberContact(final Long number,
            @Valid final MemberContactChangeDto memberChangeDto) {
        final MemberContact member;
        final MemberContact updated;

        member = MemberContactDtoMapper.toDomain(number, memberChangeDto);
        updated = service.patch(member);

        return MemberContactDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MemberContactsCaches.MEMBER, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Member caches
                    MemberContactsCaches.MEMBERS }, allEntries = true) })
    public MemberContactResponseDto updateMemberContact(final Long number,
            @Valid final MemberContactChangeDto memberChangeDto) {
        final MemberContact member;
        final MemberContact updated;

        member = MemberContactDtoMapper.toDomain(number, memberChangeDto);
        updated = service.update(member);

        return MemberContactDtoMapper.toResponseDto(updated);
    }

}
