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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntityMapper;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

/**
 * Update sponsor entity mapper.
 */
public final class UpdateSponsorEntityMapper {

    public static final Sponsor toDomain(final UpdateSponsorEntity entity) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ProfileName(entity.getContact()
            .getFirstName(),
            entity.getContact()
                .getLastName());

        contactChannels = entity.getContact()
            .getContactChannels()
            .stream()
            .map(ContactChannelEntityMapper::toDomain)
            .toList();

        return new Sponsor(entity.getContact()
            .getIdentifier(),
            entity.getContact()
                .getNumber(),
            name, entity.getContact()
                .getBirthDate(),
            contactChannels, entity.getYears(), entity.getContact()
                .getComments(),
            entity.getContact()
                .getTypes());
    }

    public static final UpdateSponsorEntity toEntity(final Sponsor data,
            final Collection<ContactMethodEntity> contactMethods) {
        final UpdateSponsorEntity              entity;
        final ProfileEntity                    contact;
        final Collection<ContactChannelEntity> contactChannels;
        contact = new ProfileEntity();
        contact.setNumber(data.number());
        contact.setFirstName(data.name()
            .firstName());
        contact.setLastName(data.name()
            .lastName());
        contact.setIdentifier(data.identifier());
        contact.setBirthDate(data.birthDate());
        contact.setComments(data.comments());

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(contact, c, contactMethods))
            .toList();
        contact.setContactChannels(contactChannels);

        contact.setTypes(new HashSet<>(data.types()));

        entity = new UpdateSponsorEntity();
        entity.setContact(contact);
        entity.setYears(new ArrayList<>(data.years()));

        return entity;
    }

    public static final UpdateSponsorEntity toEntity(final UpdateSponsorEntity entity, final Sponsor data) {

        entity.getContact()
            .setFirstName(data.name()
                .firstName());
        entity.getContact()
            .setLastName(data.name()
                .lastName());
        entity.setYears(new ArrayList<>(data.years()));

        return entity;
    }

    private static final ContactChannelEntity toEntity(final ProfileEntity contact, final ContactChannel data,
            final Collection<ContactMethodEntity> concatMethods) {
        final ContactChannelEntity          entity;
        final Optional<ContactMethodEntity> contactMethod;

        contactMethod = concatMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        // TODO: do this outside
        if (contactMethod.isEmpty()) {
            throw new MissingContactMethodException(data.contactMethod()
                .number());
        }

        entity = new ContactChannelEntity();
        entity.setContact(contact);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private UpdateSponsorEntityMapper() {
        super();
    }

}
