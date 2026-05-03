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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.bernardomg.association.sponsor.domain.model.Sponsor.ContactMethod;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile.ContactChannel;
import com.bernardomg.association.sponsor.domain.model.SponsorProfile.Name;

/**
 * Profile entity mapper.
 */
public final class SponsorProfileEntityMapper {

    public static final SponsorProfile toDomain(final SponsorInnerProfileEntity entity) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        name = new Name(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(SponsorProfileEntityMapper::toDomain)
            .toList();

        return new SponsorProfile(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(),
            contactChannels, entity.getAddress(), entity.getComments(), entity.getTypes());
    }

    public static final SponsorInnerProfileEntity toEntity(final SponsorProfile data,
            final Collection<SponsorContactMethodEntity> contactMethods) {
        final SponsorInnerProfileEntity               entity;
        final Collection<SponsorContactChannelEntity> contactChannels;

        entity = new SponsorInnerProfileEntity();
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

    private static final ContactChannel toDomain(final SponsorContactChannelEntity entity) {
        final ContactMethod method;

        method = SponsorProfileEntityMapper.toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final SponsorContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final SponsorContactChannelEntity toEntity(final SponsorInnerProfileEntity contact,
            final ContactChannel data, final Collection<SponsorContactMethodEntity> contactMethods) {
        final SponsorContactChannelEntity          entity;
        final Optional<SponsorContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new SponsorContactChannelEntity();
        entity.setProfile(contact);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private SponsorProfileEntityMapper() {
        super();
    }

}
