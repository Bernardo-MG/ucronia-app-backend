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

package com.bernardomg.association.contact.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.contact.adapter.outbound.rest.model.ContactDtoMapper;
import com.bernardomg.association.contact.domain.filter.ContactQuery;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.usecase.service.ContactService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.ContactApi;
import com.bernardomg.ucronia.openapi.model.ContactChangeDto;
import com.bernardomg.ucronia.openapi.model.ContactCreationDto;
import com.bernardomg.ucronia.openapi.model.ContactPageResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Contact REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ContactController implements ContactApi {

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
    public ContactResponseDto createContact(@Valid final ContactCreationDto contactCreationDto) {
        final Contact contact;
        final Contact created;

        contact = ContactDtoMapper.toDomain(contactCreationDto);
        created = service.create(contact);

        return ContactDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.DELETE)
    public ContactResponseDto deleteContact(final Long number) {
        final Contact contact;

        contact = service.delete(number);

        return ContactDtoMapper.toResponseDto(contact);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.READ)
    public ContactPageResponseDto getAllContacts(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort, @Valid final String name) {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        filter = new ContactQuery(name);
        contacts = service.getAll(filter, pagination, sorting);

        return ContactDtoMapper.toResponseDto(contacts);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.READ)
    public ContactResponseDto getContactByNumber(final Long number) {
        Optional<Contact> contact;

        contact = service.getOne(number);

        return ContactDtoMapper.toResponseDto(contact);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.UPDATE)
    public ContactResponseDto patchContact(final Long number, @Valid final ContactChangeDto contactChangeDto) {
        final Contact contact;
        final Contact updated;

        contact = ContactDtoMapper.toDomain(number, contactChangeDto);
        updated = service.patch(contact);

        return ContactDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "CONTACT", action = Actions.UPDATE)
    public ContactResponseDto updateContact(final Long number, @Valid final ContactChangeDto contactChangeDto) {
        final Contact contact;
        final Contact updated;

        contact = ContactDtoMapper.toDomain(number, contactChangeDto);
        updated = service.update(contact);

        return ContactDtoMapper.toResponseDto(updated);
    }

}
