/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberChange;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberCreation;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberQuery;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    /**
     * Member service.
     */
    private final MemberService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = MembersCaches.MEMBER, key = "#result.number") },
            evict = {
                    @CacheEvict(
                            cacheNames = { MembersCaches.MEMBERS, MembersCaches.PUBLIC_MEMBERS,
                                    MembersCaches.PUBLIC_MEMBER, MembersCaches.MONTHLY_BALANCE, FeeCaches.CALENDAR },
                            allEntries = true) })
    public Member create(@Valid @RequestBody final MemberCreation creation) {
        final Member member;

        member = toDomain(creation);
        return service.create(member);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.DELETE)
    @Caching(
            evict = { @CacheEvict(cacheNames = { MembersCaches.MEMBER }),
                    @CacheEvict(
                            cacheNames = { MembersCaches.MEMBERS, MembersCaches.PUBLIC_MEMBERS,
                                    MembersCaches.PUBLIC_MEMBER, MembersCaches.MONTHLY_BALANCE, FeeCaches.CALENDAR },
                            allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @PatchMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MembersCaches.MEMBER, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { MembersCaches.MEMBERS, MembersCaches.PUBLIC_MEMBERS,
                    MembersCaches.PUBLIC_MEMBER, FeeCaches.CALENDAR }, allEntries = true) })
    public Member patch(@PathVariable("number") final long number, @Valid @RequestBody final MemberChange change) {
        final Member member;

        member = toDomain(number, change);
        return service.patch(member);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.MEMBERS)
    public Iterable<Member> readAll(@Valid final MemberQuery query, final Pageable pageable) {
        return service.getAll(query, pageable);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.MEMBER)
    public Member readOne(@PathVariable("number") final Long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "MEMBER", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MembersCaches.MEMBER, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = { MembersCaches.MEMBERS, MembersCaches.PUBLIC_MEMBERS,
                    MembersCaches.PUBLIC_MEMBER, FeeCaches.CALENDAR }, allEntries = true) })
    public Member update(@PathVariable("number") final long number, @Valid @RequestBody final MemberChange change) {
        final Member member;

        member = toDomain(number, change);
        return service.update(member);
    }

    private final Member toDomain(final long number, final MemberChange change) {
        final PersonName name;

        name = new PersonName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        return new Member(number, change.getIdentifier(), name, change.getActive(), change.getPhone());
    }

    private final Member toDomain(final MemberCreation create) {
        final PersonName name;

        name = new PersonName(create.getName()
            .getFirstName(),
            create.getName()
                .getLastName());
        return new Member(null, create.getIdentifier(), name, false, create.getPhone());
    }

}
