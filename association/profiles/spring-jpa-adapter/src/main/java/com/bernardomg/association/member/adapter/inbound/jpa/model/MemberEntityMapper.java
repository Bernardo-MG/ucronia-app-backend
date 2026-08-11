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

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.FeeType;
import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

/**
 * Member entity mapper.
 */
public final class MemberEntityMapper {

    public static final Member toDomain(final MemberEntity entity) {
        final FeeType                    feeType;
        final Name                       name;
        final Collection<ContactChannel> contactChannels;
        final AuditDetails               audit;
        final Optional<Long>             key;

        feeType = new FeeType(entity.getFeeType()
            .getNumber(),
            entity.getFeeType()
                .getName(),
            entity.getFeeType()
                .getAmount());

        name = new Name(entity.getFirstName(), entity.getLastName(), Optional.ofNullable(entity.getNickname()));

        contactChannels = entity.getContactChannels()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        if (entity.getKey() == null) {
            key = Optional.empty();
        } else {
            key = Optional.of(entity.getKey()
                .getNumber());
        }

        audit = toDomain(entity.getAudit());
        return new Member(Optional.ofNullable(entity.getIdentifier()), entity.getNumber(), name,
            Optional.ofNullable(entity.getBirthDate()), contactChannels, Optional.ofNullable(entity.getAddress()),
            Optional.ofNullable(entity.getComments()), entity.getActive(), entity.getRenew(), key, feeType,
            entity.getTypes(), audit);
    }

    public static final Member toDomain(final ReadMemberEntity entity) {
        final FeeType                    feeType;
        final Name                       name;
        final Collection<ContactChannel> contactChannels;
        final AuditDetails               audit;

        feeType = new FeeType(entity.getFeeType()
            .getNumber(),
            entity.getFeeType()
                .getName(),
            entity.getFeeType()
                .getAmount());

        name = new Name(entity.getFirstName(), entity.getLastName(), Optional.ofNullable(entity.getNickname()));

        contactChannels = entity.getContactChannels()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        audit = new AuditDetails();
        return new Member(Optional.ofNullable(entity.getIdentifier()), entity.getNumber(), name,
            Optional.ofNullable(entity.getBirthDate()), contactChannels, Optional.ofNullable(entity.getAddress()),
            Optional.ofNullable(entity.getComments()), entity.getActive(), entity.getRenew(),
            Optional.ofNullable(entity.getKeyNumber()), feeType, entity.getTypes(), audit);
    }

    public static final MemberEntity toEntity(final Member data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberEntity                           entity;
        final Collection<MemberContactChannelEntity> contactChannels;

        entity = new MemberEntity();
        entity.setNumber(data.number());
        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setNickname(data.name()
            .nickname()
            .orElse(null));
        entity.setIdentifier(data.identifier()
            .orElse(null));
        entity.setBirthDate(data.birthDate()
            .orElse(null));
        entity.setAddress(data.address()
            .orElse(null));
        entity.setComments(data.comments()
            .orElse(null));

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(entity, c, contactMethods))
            .collect(Collectors.toCollection(ArrayList::new));
        if (entity.getContactChannels() != null) {
            entity.getContactChannels()
                .clear();
            entity.getContactChannels()
                .addAll(contactChannels);
        } else {
            entity.setContactChannels(contactChannels);
        }

        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    public static final MemberEntity toEntity(final MemberEntity entity, final Member data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final Collection<MemberContactChannelEntity> contactChannels;

        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setNickname(data.name()
            .nickname()
            .orElse(null));
        entity.setIdentifier(data.identifier()
            .orElse(null));
        entity.setBirthDate(data.birthDate()
            .orElse(null));
        entity.setAddress(data.address()
            .orElse(null));
        entity.setComments(data.comments()
            .orElse(null));

        contactChannels = data.contactChannels()
            .stream()
            .map(c -> toEntity(entity, c, contactMethods))
            .collect(Collectors.toCollection(ArrayList::new));
        if (entity.getContactChannels() != null) {
            entity.getContactChannels()
                .clear();
            entity.getContactChannels()
                .addAll(contactChannels);
        } else {
            entity.setContactChannels(contactChannels);
        }

        entity.setActive(data.active());
        entity.setRenew(data.renew());

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

    private static final ContactChannel toDomain(final MemberContactChannelEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final ContactMethod toDomain(final MemberContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private static final ContactChannel toDomain(final ReadMemberContactChannelEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new ContactChannel(method, entity.getDetail());
    }

    private static final MemberContactChannelEntity toEntity(final MemberEntity member, final ContactChannel data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberContactChannelEntity          entity;
        final Optional<MemberContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new MemberContactChannelEntity();
        entity.setProfile(member);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private MemberEntityMapper() {
        super();
    }

}
