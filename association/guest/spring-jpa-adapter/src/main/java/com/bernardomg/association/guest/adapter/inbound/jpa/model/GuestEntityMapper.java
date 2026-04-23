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
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;

/**
 * Update guest entity mapper.
 */
public final class GuestEntityMapper {

    public static final Guest toDomain(final GuestEntity entity) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ProfileName(entity.getProfile()
            .getFirstName(),
            entity.getProfile()
                .getLastName());

        contactChannels = entity.getProfile()
            .getContactChannels()
            .stream()
            .map(GuestEntityMapper::toDomain)
            .toList();

        return new Guest(entity.getProfile()
            .getIdentifier(),
            entity.getProfile()
                .getNumber(),
            name, entity.getProfile()
                .getBirthDate(),
            contactChannels, entity.getGames(), entity.getProfile()
                .getAddress(),
            entity.getProfile()
                .getComments(),
            entity.getProfile()
                .getTypes());
    }

    public static final Guest toDomain(final ReadGuestEntity entity) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ProfileName(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(GuestEntityMapper::toDomain)
            .toList();

        return new Guest(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(), contactChannels,
            entity.getGames(), entity.getAddress(), entity.getComments(), entity.getTypes());
    }

    public static final GuestEntity toEntity(final Guest data,
            final Collection<GuestContactMethodEntity> contactMethods) {
        final GuestEntity                           entity;
        final GuestInnerProfileEntity               profile;
        final Collection<GuestContactChannelEntity> contactChannels;

        profile = new GuestInnerProfileEntity();
        profile.setNumber(data.number());
        profile.setFirstName(data.name()
            .firstName());
        profile.setLastName(data.name()
            .lastName());
        profile.setIdentifier(data.identifier());
        profile.setBirthDate(data.birthDate());
        profile.setAddress(data.address());
        profile.setComments(data.comments());

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(profile, c, contactMethods))
            .toList();
        if (profile.getContactChannels() != null) {
            profile.getContactChannels()
                .clear();
            profile.getContactChannels()
                .addAll(contactChannels);
        } else {
            profile.setContactChannels(contactChannels);
        }

        profile.setTypes(new HashSet<>(data.types()));

        entity = new GuestEntity();
        entity.setProfile(profile);
        entity.setGames(new ArrayList<>(data.games()));

        return entity;
    }

    public static final GuestEntity toEntity(final GuestEntity entity, final Guest data,
            final Collection<GuestContactMethodEntity> contactMethods) {
        final GuestInnerProfileEntity               profile;
        final Collection<GuestContactChannelEntity> contactChannels;

        profile = entity.getProfile();
        profile.setFirstName(data.name()
            .firstName());
        profile.setLastName(data.name()
            .lastName());
        profile.setIdentifier(data.identifier());
        profile.setBirthDate(data.birthDate());
        profile.setAddress(data.address());
        profile.setComments(data.comments());

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(profile, c, contactMethods))
            .toList();
        if (profile.getContactChannels() != null) {
            profile.getContactChannels()
                .clear();
            profile.getContactChannels()
                .addAll(contactChannels);
        } else {
            profile.setContactChannels(contactChannels);
        }

        profile.setTypes(new HashSet<>(data.types()));

        entity.setGames(new ArrayList<>(data.games()));

        return entity;
    }

    private static final ContactChannel toDomain(final GuestContactChannelEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final GuestContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final ContactChannel toDomain(final ReadGuestContactChannelEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final GuestContactChannelEntity toEntity(final GuestInnerProfileEntity profile,
            final ContactChannel data, final Collection<GuestContactMethodEntity> contactMethods) {
        final GuestContactChannelEntity          entity;
        final Optional<GuestContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new GuestContactChannelEntity();
        entity.setProfile(profile);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private GuestEntityMapper() {
        super();
    }

}
