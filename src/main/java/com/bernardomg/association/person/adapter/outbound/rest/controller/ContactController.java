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
import com.bernardomg.association.person.adapter.outbound.cache.ContactsCaches;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactDtoMapper;
import com.bernardomg.association.person.domain.filter.ContactFilter;
import com.bernardomg.association.person.domain.filter.ContactFilter.ContactStatus;
import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.usecase.service.ContactService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.PersonApi;
import com.bernardomg.ucronia.openapi.model.PersonChangeDto;
import com.bernardomg.ucronia.openapi.model.PersonCreationDto;
import com.bernardomg.ucronia.openapi.model.PersonPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonStatusDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Contact REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ContactController implements PersonApi {

    /**
     * Contact service.
     */
    private final ContactService service;

    public ContactController(final ContactService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = ContactsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Contact caches
                    ContactsCaches.CONTACTS,
                    // Fee caches
                    FeeCaches.MEMBER_FEES,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto createPerson(@Valid final PersonCreationDto personCreationDto) {
        final Contact member;
        final Contact created;

        member = ContactDtoMapper.toDomain(personCreationDto);
        created = service.create(member);

        return ContactDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { ContactsCaches.PERSON }), @CacheEvict(cacheNames = {
            // Contact caches
            ContactsCaches.CONTACTS,
            // Fee caches
            FeeCaches.MEMBER_FEES,
            // Member caches
            MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto deletePerson(final Long number) {
        final Contact person;

        person = service.delete(number);

        return ContactDtoMapper.toResponseDto(person);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.READ)
    @Cacheable(cacheNames = ContactsCaches.CONTACTS)
    public PersonPageResponseDto getAllPersons(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort, @Valid final PersonStatusDto status, @Valid final String name) {
        final Page<Contact> persons;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactStatus personStatus;
        final ContactFilter filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        if (status != null) {
            personStatus = ContactStatus.valueOf(status.name());
        } else {
            personStatus = null;
        }
        filter = new ContactFilter(personStatus, name);
        persons = service.getAll(filter, pagination, sorting);

        return ContactDtoMapper.toResponseDto(persons);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.READ)
    @Cacheable(cacheNames = ContactsCaches.PERSON)
    public PersonResponseDto getPersonByNumber(final Long number) {
        Optional<Contact> person;

        person = service.getOne(number);

        return ContactDtoMapper.toResponseDto(person);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = ContactsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Contact caches
                    ContactsCaches.CONTACTS,
                    // Fee caches
                    FeeCaches.MEMBER_FEES,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto patchPerson(final Long number, @Valid final PersonChangeDto personChangeDto) {
        final Contact member;
        final Contact updated;

        member = ContactDtoMapper.toDomain(number, personChangeDto);
        updated = service.patch(member);

        return ContactDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = ContactsCaches.PERSON, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Contact caches
                    ContactsCaches.CONTACTS,
                    // Fee caches
                    FeeCaches.MEMBER_FEES,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public PersonResponseDto updatePerson(final Long number, @Valid final PersonChangeDto personChangeDto) {
        final Contact member;
        final Contact updated;

        member = ContactDtoMapper.toDomain(number, personChangeDto);
        updated = service.update(member);

        return ContactDtoMapper.toResponseDto(updated);
    }

}
