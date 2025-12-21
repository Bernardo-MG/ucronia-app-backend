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

package com.bernardomg.association.guest.adapter.inbound.jpa.model;

import java.util.ArrayList;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.guest.domain.model.Guest;

/**
 * Update guest entity mapper.
 */
public final class UpdateGuestEntityMapper {

    public static final Guest toDomain(final UpdateGuestEntity entity) {
        final ContactName name;

        name = new ContactName(entity.getContact()
            .getFirstName(),
            entity.getContact()
                .getLastName());
        return new Guest(entity.getContact()
            .getNumber(), name, new ArrayList<>(entity.getGames()));
    }

    public static final UpdateGuestEntity toEntity(final Guest data) {
        final UpdateGuestEntity entity;
        final ContactEntity     contact;

        contact = new ContactEntity();
        contact.setNumber(data.number());
        contact.setFirstName(data.name()
            .firstName());
        contact.setLastName(data.name()
            .lastName());

        entity = new UpdateGuestEntity();
        entity.setContact(contact);
        entity.setGames(new ArrayList<>(data.games()));

        return entity;
    }

    public static final UpdateGuestEntity toEntity(final UpdateGuestEntity entity, final Guest data) {

        entity.getContact()
            .setFirstName(data.name()
                .firstName());
        entity.getContact()
            .setLastName(data.name()
                .lastName());
        entity.setGames(new ArrayList<>(data.games()));

        return entity;
    }

    private UpdateGuestEntityMapper() {
        super();
    }

}
