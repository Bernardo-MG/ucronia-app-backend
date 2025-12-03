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

package com.bernardomg.association.security.user.adapter.outbound.rest.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.security.user.adapter.outbound.rest.model.UserContactDtoMapper;
import com.bernardomg.association.security.user.usecase.service.UserContactService;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.UserContactApi;
import com.bernardomg.ucronia.openapi.model.ContactResponseDto;

/**
 * User member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class UserContactController implements UserContactApi {

    /**
     * User member service.
     */
    private final UserContactService service;

    public UserContactController(final UserContactService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "USER", action = Actions.UPDATE)
    public ContactResponseDto assignContactToUser(final String username, final Long memberNumber) {
        Contact contact;

        contact = service.assignContact(username, memberNumber);
        return UserContactDtoMapper.toResponseDto(contact);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAuthorization(resource = "USER", action = Actions.READ)
    public ContactResponseDto getAssignedContact(final String username) {
        final Optional<Contact> contact;

        contact = service.getContact(username);

        return UserContactDtoMapper.toResponseDto(contact);
    }

    @Override
    @RequireResourceAuthorization(resource = "USER", action = Actions.UPDATE)
    public ContactResponseDto unassignContact(final String username) {
        Contact contact;

        contact = service.unassignContact(username);

        return UserContactDtoMapper.toResponseDto(contact);
    }

}
