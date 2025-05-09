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

package com.bernardomg.association.person.adapter.outbound.rest.controller;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import com.bernardomg.association.person.adapter.outbound.cache.PersonsCaches;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonCreation;
import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.usecase.service.PersonService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Person REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    /**
     * Person service.
     */
    private final PersonService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "PERSON", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public Person create(@Valid @RequestBody final PersonCreation creation) {
        final Person member;

        member = toDomain(creation);
        return service.create(member);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "PERSON", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { PersonsCaches.PERSON }), @CacheEvict(cacheNames = {
            // Person caches
            PersonsCaches.PERSONS,
            // Fee caches
            FeeCaches.CALENDAR,
            // Member caches
            MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @PatchMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "PERSON", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public Person patch(@PathVariable("number") final long number, @Valid @RequestBody final PersonChange change) {
        final Person member;

        member = toDomain(number, change);
        return service.patch(member);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "PERSON", action = Actions.READ)
    @Cacheable(cacheNames = PersonsCaches.PERSONS)
    public Iterable<Person> readAll(final PersonFilter filter, final Pagination pagination, final Sorting sorting) {
        return service.getAll(filter, pagination, sorting);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "PERSON", action = Actions.READ)
    @Cacheable(cacheNames = PersonsCaches.PERSON)
    public Person readOne(@PathVariable("number") final Long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "PERSON", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public Person update(@PathVariable("number") final long number, @Valid @RequestBody final PersonChange change) {
        final Person member;

        member = toDomain(number, change);
        return service.update(member);
    }

    private final Person toDomain(final long number, final PersonChange change) {
        final PersonName           name;
        final Optional<Membership> membership;

        name = new PersonName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        if (change.getMembership() == null) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(change.getMembership()
                .active(),
                change.getMembership()
                    .renew()));
        }
        return new Person(change.getIdentifier(), number, name, change.getBirthDate(), change.getPhone(), membership);
    }

    private final Person toDomain(final PersonCreation change) {
        final PersonName           name;
        final Optional<Membership> membership;

        name = new PersonName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        if (change.getMembership() == null) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(change.getMembership()
                .active(),
                change.getMembership()
                    .active()));
        }
        return new Person("", -1L, name, null, "", membership);
    }

}
