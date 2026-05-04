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
import com.bernardomg.association.member.domain.model.Member.ContactChannel;
import com.bernardomg.association.member.domain.model.Member.ContactMethod;
import com.bernardomg.association.member.domain.model.Member.Name;

/**
 * Update guest entity mapper.
 */
public final class MemberEntityMapper {

    public static final Member toDomain(final MemberEntity entity) {
        final Member.FeeType             feeType;
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        feeType = new Member.FeeType(entity.getFeeType()
            .getNumber(),
            entity.getFeeType()
                .getName(),
            entity.getFeeType()
                .getAmount());

        name = new Name(entity.getProfile()
            .getFirstName(),
            entity.getProfile()
                .getLastName());

        contactChannels = entity.getProfile()
            .getContactChannels()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        return new Member(entity.getProfile()
            .getIdentifier(),
            entity.getProfile()
                .getNumber(),
            name, entity.getProfile()
                .getBirthDate(),
            contactChannels, entity.getProfile()
                .getAddress(),
            entity.getProfile()
                .getComments(),
            entity.getActive(), entity.getRenew(), feeType, entity.getProfile()
                .getTypes());
    }

    public static final Member toDomain(final ReadMemberEntity entity) {
        final Member.FeeType             feeType;
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        feeType = new Member.FeeType(entity.getFeeType()
            .getNumber(),
            entity.getFeeType()
                .getName(),
            entity.getFeeType()
                .getAmount());

        name = new Name(entity.getFirstName(), entity.getLastName());

        contactChannels = entity.getContactChannels()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        return new Member(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(), contactChannels,
            entity.getAddress(), entity.getComments(), entity.getActive(), entity.getRenew(), feeType,
            entity.getTypes());
    }

    public static final MemberEntity toEntity(final Member data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberEntity                           entity;
        final MemberInnerProfileEntity               profile;
        final Collection<MemberContactChannelEntity> contactChannels;

        profile = new MemberInnerProfileEntity();
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
            .collect(Collectors.toCollection(ArrayList::new));
        if (profile.getContactChannels() != null) {
            profile.getContactChannels()
                .clear();
            profile.getContactChannels()
                .addAll(contactChannels);
        } else {
            profile.setContactChannels(contactChannels);
        }

        profile.setTypes(profile.getTypes());

        entity = new MemberEntity();
        entity.setProfile(profile);
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    public static final MemberEntity toEntity(final MemberEntity entity, final Member data,
            final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberInnerProfileEntity               profile;
        final Collection<MemberContactChannelEntity> contactChannels;

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
            .collect(Collectors.toCollection(ArrayList::new));
        if (profile.getContactChannels() != null) {
            profile.getContactChannels()
                .clear();
            profile.getContactChannels()
                .addAll(contactChannels);
        } else {
            profile.setContactChannels(contactChannels);
        }

        profile.setTypes(profile.getTypes());

        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
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

    private static final MemberContactChannelEntity toEntity(final MemberInnerProfileEntity profile,
            final ContactChannel data, final Collection<MemberContactMethodEntity> contactMethods) {
        final MemberContactChannelEntity          entity;
        final Optional<MemberContactMethodEntity> contactMethod;

        contactMethod = contactMethods.stream()
            .filter(m -> m.getNumber()
                .equals(data.contactMethod()
                    .number()))
            .findFirst();

        entity = new MemberContactChannelEntity();
        entity.setProfile(profile);
        entity.setContactMethod(contactMethod.get());
        entity.setDetail(data.detail());

        return entity;
    }

    private MemberEntityMapper() {
        super();
    }

}
