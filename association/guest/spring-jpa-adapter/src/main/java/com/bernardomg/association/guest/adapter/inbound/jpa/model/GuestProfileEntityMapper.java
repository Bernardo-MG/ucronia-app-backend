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

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.bernardomg.association.guest.domain.model.Guest.ContactMethod;
import com.bernardomg.association.guest.domain.model.GuestProfile;
import com.bernardomg.association.guest.domain.model.GuestProfile.ContactChannel;
import com.bernardomg.association.guest.domain.model.GuestProfile.Name;

/**
 * Profile entity mapper.
 */
public final class GuestProfileEntityMapper {

    public static final GuestProfile toDomain(final GuestInnerProfileEntity entity) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        name = new Name(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(GuestProfileEntityMapper::toDomain)
            .toList();

        return new GuestProfile(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(),
            contactChannels, entity.getAddress(), entity.getComments(), entity.getTypes());
    }

    public static final GuestInnerProfileEntity toEntity(final GuestProfile data,
            final Collection<GuestContactMethodEntity> contactMethods) {
        final GuestInnerProfileEntity               entity;
        final Collection<GuestContactChannelEntity> contactChannels;

        entity = new GuestInnerProfileEntity();
        entity.setNumber(data.number());
        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setIdentifier(data.identifier());
        entity.setBirthDate(data.birthDate());
        entity.setAddress(data.address());
        entity.setComments(data.comments());

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(entity, c, contactMethods))
            .toList();
        if (entity.getContactChannels() != null) {
            entity.getContactChannels()
                .clear();
            entity.getContactChannels()
                .addAll(contactChannels);
        } else {
            entity.setContactChannels(contactChannels);
        }

        entity.setTypes(new HashSet<>(data.types()));

        return entity;
    }

    private static final ContactChannel toDomain(final GuestContactChannelEntity entity) {
        final ContactMethod method;

        method = GuestProfileEntityMapper.toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final GuestContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final GuestContactChannelEntity toEntity(final GuestInnerProfileEntity contact,
            final ContactChannel data, final Collection<GuestContactMethodEntity> contactMethods) {
        final GuestContactChannelEntity          entity;
        final Optional<GuestContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new GuestContactChannelEntity();
        entity.setProfile(contact);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private GuestProfileEntityMapper() {
        super();
    }

}
