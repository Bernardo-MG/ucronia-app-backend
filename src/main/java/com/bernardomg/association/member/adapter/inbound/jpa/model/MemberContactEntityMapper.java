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

package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactChannelEntityMapper;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.contact.domain.exception.MissingContactMethodException;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.member.domain.model.MemberContact;

/**
 * Member entity mapper.
 */
public final class MemberContactEntityMapper {

    public static final MemberContact toDomain(final MemberContactEntity entity) {
        final ContactName                name;
        final Collection<ContactChannel> members;

        name = new ContactName(entity.getContact()
            .getFirstName(),
            entity.getContact()
                .getLastName());
        members = entity.getContact()
            .getContactChannels()
            .stream()
            .map(ContactChannelEntityMapper::toDomain)
            .toList();

        return new MemberContact(entity.getContact()
            .getIdentifier(),
            entity.getContact()
                .getNumber(),
            name, entity.getContact()
                .getBirthDate(),
            entity.getActive(), entity.getRenew(), members);
    }

    public static final MemberContactEntity toEntity(final MemberContact data,
            final Collection<ContactMethodEntity> contactMethods) {
        final boolean                          active;
        final boolean                          renew;
        final MemberContactEntity              entity;
        final Collection<ContactChannelEntity> members;

        active = data.active();
        renew = data.renew();

        entity = new MemberContactEntity();
        entity.setContact(new ContactEntity());
        entity.getContact()
            .setNumber(data.number());
        entity.getContact()
            .setFirstName(data.name()
                .firstName());
        entity.getContact()
            .setLastName(data.name()
                .lastName());
        entity.getContact()
            .setIdentifier(data.identifier());
        entity.getContact()
            .setBirthDate(data.birthDate());
        entity.setActive(active);
        entity.setRenew(renew);

        members = data.contactChannels()
            .stream()
            .map(m -> MemberContactEntityMapper.toEntity(entity, m, contactMethods))
            .toList();
        entity.getContact()
            .setContactChannels(members);

        return entity;
    }

    private static final ContactChannelEntity toEntity(final MemberContactEntity member, final ContactChannel data,
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
        entity.setContact(member.getContact());
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private MemberContactEntityMapper() {
        super();
    }

}
