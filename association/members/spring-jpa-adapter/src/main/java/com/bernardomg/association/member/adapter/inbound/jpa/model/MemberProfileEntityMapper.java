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
import java.util.HashSet;
import java.util.Optional;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactChannel;
import com.bernardomg.association.member.domain.model.MemberProfile.ContactMethod;
import com.bernardomg.association.member.domain.model.MemberProfile.Name;

/**
 * Profile entity mapper.
 */
public final class MemberProfileEntityMapper {

    public static final MemberProfile toDomain(final MemberInnerProfileEntity entity) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        name = new Name(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(MemberProfileEntityMapper::toDomain)
            .toList();

        return new MemberProfile(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(),
            contactChannels, entity.getAddress(), entity.getComments(), entity.getTypes());
    }

    public static final MemberInnerProfileEntity toEntity(final MemberProfile data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberInnerProfileEntity               entity;
        final Collection<MemberContactChannelEntity> contactChannels;

        entity = new MemberInnerProfileEntity();
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

    private static final ContactChannel toDomain(final MemberContactChannelEntity entity) {
        final ContactMethod method;

        method = MemberProfileEntityMapper.toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final MemberContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final MemberContactChannelEntity toEntity(final MemberInnerProfileEntity contact,
            final ContactChannel data, final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberContactChannelEntity          entity;
        final Optional<MemberContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new MemberContactChannelEntity();
        entity.setProfile(contact);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private MemberProfileEntityMapper() {
        super();
    }

}
