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

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.person.adapter.outbound.cache.PersonsCaches;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonDtoMapper;
import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.filter.PersonFilter.PersonStatus;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.usecase.service.PersonService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.PersonApi;
import com.bernardomg.ucronia.openapi.model.PersonChangeDto;
import com.bernardomg.ucronia.openapi.model.PersonCreationDto;
import com.bernardomg.ucronia.openapi.model.PersonPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonStatusDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Person REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class PersonController implements PersonApi {

    /**
     * Person service.
     */
    private final PersonService service;

    public PersonController(final PersonService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto createPerson(@Valid final PersonCreationDto personCreationDto) {
        final Person member;
        final Person created;

        member = PersonDtoMapper.toDomain(personCreationDto);
        created = service.create(member);

        return PersonDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { PersonsCaches.PERSON }), @CacheEvict(cacheNames = {
            // Person caches
            PersonsCaches.PERSONS,
            // Fee caches
            FeeCaches.CALENDAR,
            // Member caches
            MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto deletePerson(final Long number) {
        final Person person;

        person = service.delete(number);

        return PersonDtoMapper.toResponseDto(person);
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.READ)
    @Cacheable(cacheNames = PersonsCaches.PERSONS)
    public PersonPageResponseDto getAllPersons(@Valid final PersonStatusDto status, @Valid final String name,
            @Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Page<Person> persons;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonStatus personStatus;
        final PersonFilter filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        personStatus = PersonStatus.valueOf(status.name());
        filter = new PersonFilter(personStatus, name);
        persons = service.getAll(filter, pagination, sorting);

        return PersonDtoMapper.toResponseDto(persons);
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.READ)
    @Cacheable(cacheNames = PersonsCaches.PERSON)
    public PersonResponseDto getPersonByNumber(final Long number) {
        Optional<Person> person;

        person = service.getOne(number);

        return PersonDtoMapper.toResponseDto(person);
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto patchPerson(final Long number, @Valid final PersonChangeDto personChangeDto) {
        final Person member;
        final Person updated;

        member = PersonDtoMapper.toDomain(number, personChangeDto);
        updated = service.patch(member);

        return PersonDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAccess(resource = "PERSON", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = PersonsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Person caches
                    PersonsCaches.PERSONS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto updatePerson(final Long number, @Valid final PersonChangeDto personChangeDto) {
        final Person member;
        final Person updated;

        member = PersonDtoMapper.toDomain(number, personChangeDto);
        updated = service.update(member);

        return PersonDtoMapper.toResponseDto(updated);
    }

}
