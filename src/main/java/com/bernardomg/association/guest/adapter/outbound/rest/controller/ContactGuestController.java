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

package com.bernardomg.association.guest.adapter.outbound.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.guest.adapter.outbound.rest.model.GuestDtoMapper;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.usecase.service.ContactGuestService;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.ProfileGuestApi;
import com.bernardomg.ucronia.openapi.model.GuestResponseDto;

/**
 * Member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ContactGuestController implements ProfileGuestApi {

    private final ContactGuestService service;

    public ContactGuestController(final ContactGuestService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "GUEST", action = Actions.CREATE)
    public GuestResponseDto convertToGuest(final Long number) {
        final Guest created;

        created = service.convertToGuest(number);

        return GuestDtoMapper.toResponseDto(created);
    }

}
