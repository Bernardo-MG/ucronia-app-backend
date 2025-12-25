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
 * Update guest entity mapper.
 */
public final class UpdateMemberContactEntityMapper {

    public static final MemberContact toDomain(final UpdateMemberContactEntity entity) {
        final ContactName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ContactName(entity.getContact()
            .getFirstName(),
            entity.getContact()
                .getLastName());

        contactChannels = entity.getContact()
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
            contactChannels, entity.getContact()
                .getComments(),
            entity.getActive(), entity.getRenew(), entity.getContact()
                .getTypes());
    }

    public static final UpdateMemberContactEntity toEntity(final MemberContact data,
            final Collection<ContactMethodEntity> contactMethods) {
        final UpdateMemberContactEntity        entity;
        final ContactEntity                    contact;
        final Collection<ContactChannelEntity> contactChannels;

        contact = new ContactEntity();
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

        entity = new UpdateMemberContactEntity();
        entity.setContact(contact);
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    public static final UpdateMemberContactEntity toEntity(final UpdateMemberContactEntity entity,
            final MemberContact data) {

        entity.getContact()
            .setFirstName(data.name()
                .firstName());
        entity.getContact()
            .setLastName(data.name()
                .lastName());
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    private static final ContactChannelEntity toEntity(final ContactEntity contact, final ContactChannel data,
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

    private UpdateMemberContactEntityMapper() {
        super();
    }

}
