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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.person.adapter.outbound.cache.ContactMethodCaches;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodCreation;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.usecase.service.ContactMethodService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;

/**
 * Contact method REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/person/contactMethod")
public class ContactMethodController {

    /**
     * Contact method service.
     */
    private final ContactMethodService service;

    public ContactMethodController(final ContactMethodService service) {
        super();

        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = ContactMethodCaches.CONTACT_METHOD, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = {
                    // ContactMethod caches
                    ContactMethodCaches.CONTACT_METHODS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public ContactMethod create(@Valid @RequestBody final ContactMethodCreation creation) {
        final ContactMethod member;

        member = toDomain(creation);
        return service.create(member);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { ContactMethodCaches.CONTACT_METHOD }), @CacheEvict(cacheNames = {
            // ContactMethod caches
            ContactMethodCaches.CONTACT_METHODS,
            // Fee caches
            FeeCaches.CALENDAR,
            // Member caches
            MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.READ)
    @Cacheable(cacheNames = ContactMethodCaches.CONTACT_METHODS)
    public Page<ContactMethod> readAll(final Pagination pagination, final Sorting sorting) {
        return service.getAll(pagination, sorting);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.READ)
    @Cacheable(cacheNames = ContactMethodCaches.CONTACT_METHOD)
    public ContactMethod readOne(@PathVariable("number") final Long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "CONTACT_METHOD", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = ContactMethodCaches.CONTACT_METHOD, key = "#result.number") },
            evict = { @CacheEvict(cacheNames = {
                    // ContactMethod caches
                    ContactMethodCaches.CONTACT_METHODS,
                    // Fee caches
                    FeeCaches.CALENDAR,
                    // Member caches
                    MembersCaches.MEMBER, MembersCaches.MEMBERS }, allEntries = true) })
    public ContactMethod update(@PathVariable("number") final long number,
            @Valid @RequestBody final ContactMethodChange change) {
        final ContactMethod member;

        member = toDomain(number, change);
        return service.update(member);
    }

    private final ContactMethod toDomain(final ContactMethodCreation change) {
        return new ContactMethod(null, change.name());
    }

    private final ContactMethod toDomain(final long number, final ContactMethodChange change) {
        return new ContactMethod(number, change.name());
    }

}
