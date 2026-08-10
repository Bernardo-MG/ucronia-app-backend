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
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

/**
 * Update guest entity mapper.
 */
public final class GuestEntityMapper {

    public static final Guest toDomain(final GuestEntity entity) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;
        final AuditDetails               audit;

        name = new Name(entity.getProfile()
            .getFirstName(),
            entity.getProfile()
                .getLastName(),
            Optional.ofNullable(entity.getProfile()
                .getNickname()));

        contactChannels = entity.getProfile()
            .getContactChannels()
            .stream()
            .map(GuestEntityMapper::toDomain)
            .toList();

        audit = toDomain(entity.getProfile()
            .getAudit());
        return new Guest(Optional.ofNullable(entity.getProfile()
            .getIdentifier()), entity.getProfile()
                .getNumber(),
            name, Optional.ofNullable(entity.getProfile()
                .getBirthDate()),
            contactChannels, entity.getGames(), Optional.ofNullable(entity.getProfile()
                .getAddress()),
            Optional.ofNullable(entity.getProfile()
                .getComments()),
            entity.getProfile()
                .getTypes(),
            audit);
    }

    public static final Guest toDomain(final ReadGuestEntity entity) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;
        final AuditDetails               audit;

        name = new Name(entity.getFirstName(), entity.getLastName(), Optional.ofNullable(entity.getNickname()));

        contactChannels = entity.getContactChannels()
            .stream()
            .map(GuestEntityMapper::toDomain)
            .toList();

        audit = new AuditDetails();
        return new Guest(Optional.ofNullable(entity.getIdentifier()), entity.getNumber(), name,
            Optional.ofNullable(entity.getBirthDate()), contactChannels, entity.getGames(),
            Optional.ofNullable(entity.getAddress()), Optional.ofNullable(entity.getComments()), entity.getTypes(),
            audit);
    }

    public static final GuestEntity toEntity(final Guest data, final Collection<ContactMethodEntity> contactMethods) {
        final GuestEntity                      entity;
        final ProfileEntity                    profile;
        final Collection<ContactChannelEntity> contactChannels;

        profile = new ProfileEntity();
        profile.setNumber(data.number());
        profile.setFirstName(data.name()
            .firstName());
        profile.setLastName(data.name()
            .lastName());
        profile.setNickname(data.name()
            .nickname()
            .orElse(null));
        profile.setIdentifier(data.identifier()
            .orElse(null));
        profile.setBirthDate(data.birthDate()
            .orElse(null));
        profile.setAddress(data.address()
            .orElse(null));
        profile.setComments(data.comments()
            .orElse(null));

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(c, contactMethods))
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
            final Collection<ContactMethodEntity> contactMethods) {
        final ProfileEntity                    profile;
        final Collection<ContactChannelEntity> contactChannels;

        profile = entity.getProfile();
        profile.setFirstName(data.name()
            .firstName());
        profile.setLastName(data.name()
            .lastName());
        profile.setNickname(data.name()
            .nickname()
            .orElse(null));
        profile.setIdentifier(data.identifier()
            .orElse(null));
        profile.setBirthDate(data.birthDate()
            .orElse(null));
        profile.setAddress(data.address()
            .orElse(null));
        profile.setComments(data.comments()
            .orElse(null));

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(c, contactMethods))
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

    private static final AuditUser toAuditDomain(final AuditUserEntity user) {
        final AuditUser auditUser;

        if (user == null) {
            auditUser = null;
        } else {
            auditUser = new AuditUser(user.getEmail(), user.getUsername(), user.getName());
        }

        return auditUser;
    }

    private static final AuditDetails toDomain(final AuditMetadata audit) {
        final AuditDetails auditDetails;

        if (audit == null) {
            auditDetails = new AuditDetails();
        } else {
            auditDetails = new AuditDetails(audit.getCreatedAt(), toAuditDomain(audit.getCreatedBy()),
                audit.getUpdatedAt(), toAuditDomain(audit.getUpdatedBy()));
        }

        return auditDetails;
    }

    private static final ContactChannel toDomain(final ContactChannelEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final ContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final ContactChannelEntity toEntity(final ContactChannel data,
            final Collection<ContactMethodEntity> contactMethods) {
        final ContactChannelEntity          entity;
        final Optional<ContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new ContactChannelEntity();
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private GuestEntityMapper() {
        super();
    }

}
