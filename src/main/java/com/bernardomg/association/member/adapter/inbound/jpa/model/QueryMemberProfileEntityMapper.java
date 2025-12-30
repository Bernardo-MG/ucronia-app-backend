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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;

/**
 * Query member profile entity mapper.
 */
public final class QueryMemberProfileEntityMapper {

    public static final MemberProfile toDomain(final QueryMemberProfileEntity entity) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ProfileName(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(QueryMemberContactChannelEntityMapper::toDomain)
            .toList();

        return new MemberProfile(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(),
            contactChannels, entity.getComments(), entity.getActive(), entity.getRenew(), entity.getTypes());
    }

    public static final QueryMemberProfileEntity toEntity(final MemberProfile data,
            final Collection<ContactMethodEntity> contactMethods) {
        final QueryMemberProfileEntity                    entity;
        final Collection<QueryMemberContactChannelEntity> contactChannels;

        entity = new QueryMemberProfileEntity();
        entity.setNumber(data.number());
        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setIdentifier(data.identifier());
        entity.setBirthDate(data.birthDate());
        entity.setComments(data.comments());
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(c, contactMethods))
            .collect(Collectors.toCollection(ArrayList::new));
        entity.setContactChannels(contactChannels);

        return entity;
    }

    private static final QueryMemberContactChannelEntity toEntity(final ContactChannel data,
            final Collection<ContactMethodEntity> concatMethods) {
        final QueryMemberContactChannelEntity entity;
        final Optional<ContactMethodEntity>   contactMethod;

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

        entity = new QueryMemberContactChannelEntity();
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private QueryMemberProfileEntityMapper() {
        super();
    }

}
