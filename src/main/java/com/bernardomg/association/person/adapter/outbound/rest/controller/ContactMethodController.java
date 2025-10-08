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
import com.bernardomg.association.person.adapter.outbound.cache.ContactMethodCaches;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodDtoMapper;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.usecase.service.ContactMethodService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.ContactMethodApi;
import com.bernardomg.ucronia.openapi.model.ContactMethodChangeDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodCreationDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodPageResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Contact method REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ContactMethodController implements ContactMethodApi {

    /**
     * Contact method service.
     */
    private final ContactMethodService service;

    public ContactMethodController(final ContactMethodService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = ContactMethodCaches.CONTACT_METHOD, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // ContactMethod caches
                    ContactMethodCaches.CONTACT_METHODS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public ContactMethodResponseDto
            createContactMethod(@Valid final ContactMethodCreationDto contactMethodCreationDto) {
        final ContactMethod member;
        final ContactMethod contactMethod;

        member = ContactMethodDtoMapper.toDomain(contactMethodCreationDto);
        contactMethod = service.create(member);

        return ContactMethodDtoMapper.toResponseDto(contactMethod);
    }

    @Override
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { ContactMethodCaches.CONTACT_METHOD }), @CacheEvict(cacheNames = {
            // ContactMethod caches
            ContactMethodCaches.CONTACT_METHODS,
            // Fee caches
            FeeCaches.CALENDAR,
            // Member caches
            MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public ContactMethodResponseDto deleteContactMethod(final Long number) {
        final ContactMethod contactMethod;

        contactMethod = service.delete(number);

        return ContactMethodDtoMapper.toResponseDto(contactMethod);
    }

    @Override
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.READ)
    @Cacheable(cacheNames = ContactMethodCaches.CONTACT_METHODS)
    public ContactMethodPageResponseDto getAllContactMethods(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        final Page<ContactMethod> contactMethods;
        final Pagination          pagination;
        final Sorting             sorting;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        contactMethods = service.getAll(pagination, sorting);

        return ContactMethodDtoMapper.toResponseDto(contactMethods);
    }

    @Override
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.READ)
    @Cacheable(cacheNames = ContactMethodCaches.CONTACT_METHOD)
    public ContactMethodResponseDto getContactMethodByNumber(final Long number) {
        Optional<ContactMethod> contactMethod;

        contactMethod = service.getOne(number);

        return ContactMethodDtoMapper.toResponseDto(contactMethod);
    }

    @Override
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = ContactMethodCaches.CONTACT_METHOD, key = "#result.content.number") },
            evict = { @CacheEvict(cacheNames = {
                    // ContactMethod caches
                    ContactMethodCaches.CONTACT_METHODS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public ContactMethodResponseDto updateContactMethod(final Long number,
            @Valid final ContactMethodChangeDto contactMethodChangeDto) {
        final ContactMethod contactMethod;
        final ContactMethod updated;

        contactMethod = ContactMethodDtoMapper.toDomain(number, contactMethodChangeDto);
        updated = service.update(contactMethod);

        return ContactMethodDtoMapper.toResponseDto(updated);
    }

}
