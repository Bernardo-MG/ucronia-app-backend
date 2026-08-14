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

package com.bernardomg.association.member.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public record Member(Optional<String> identifier, Long number, Name name, Optional<Instant> birthDate,
        Collection<ContactChannel> contactChannels, Optional<String> address, Optional<String> comments, Boolean active,
        Boolean renew, Optional<Long> key, FeeType feeType, Set<String> types, AuditDetails audit) {

    public static final String PROFILE_TYPE = "member";

    public Member(final Optional<String> identifier, final Long number, final Name name,
            final Optional<Instant> birthDate, final Collection<ContactChannel> contactChannels,
            final Optional<String> address, final Optional<String> comments, final Boolean active, final Boolean renew,
            final Optional<Long> key, final FeeType feeType, final Set<String> types, final AuditDetails audit) {
        Objects.requireNonNull(identifier, "Identifier can't be null");
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(birthDate, "Birthdate can't be null");
        Objects.requireNonNull(address, "Address can't be null");
        Objects.requireNonNull(comments, "Comments can't be null");
        Objects.requireNonNull(active, "Active flag can't be null");
        Objects.requireNonNull(renew, "Renew flag can't be null");
        Objects.requireNonNull(key, "Key number can't be null");
        Objects.requireNonNull(feeType, "Fee type can't be null");
        Objects.requireNonNull(types, "Types can't be null");
        Objects.requireNonNull(contactChannels, "Contact channels can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        this.identifier = handleEmpty(identifier);
        this.number = number;
        this.name = name;
        this.birthDate = birthDate;
        this.contactChannels = List.copyOf(contactChannels);
        this.address = handleEmpty(address);
        this.comments = handleEmpty(comments);
        this.active = active;
        this.renew = renew;
        this.key = key;
        this.feeType = feeType;
        this.types = Set.copyOf(types);
        this.audit = audit;
    }

    public Member(final Optional<String> identifier, final Long number, final Name name,
            final Optional<Instant> birthDate, final Collection<ContactChannel> contactChannels,
            final Optional<String> address, final Optional<String> comments, final Boolean active, final Boolean renew,
            final Optional<Long> key, final FeeType feeType, final Set<String> types) {
        this(identifier, number, name, birthDate, contactChannels, address, comments, active, renew, key, feeType,
            types, new AuditDetails());
    }

    public Member(final Optional<String> identifier, final Long number, final Name name,
            final Optional<Instant> birthDate, final Collection<ContactChannel> contactChannels,
            final Optional<String> address, final Optional<String> comments, final Boolean active, final Boolean renew,
            final FeeType feeType, final Set<String> types, final AuditDetails audit) {
        this(identifier, number, name, birthDate, contactChannels, address, comments, active, renew, Optional.empty(),
            feeType, types, audit);
    }

    public Member(final Optional<String> identifier, final Long number, final Name name,
            final Optional<Instant> birthDate, final Collection<ContactChannel> contactChannels,
            final Optional<String> address, final Optional<String> comments, final Boolean active, final Boolean renew,
            final FeeType feeType, final Set<String> types) {
        this(identifier, number, name, birthDate, contactChannels, address, comments, active, renew, Optional.empty(),
            feeType, types, new AuditDetails());
    }

    private final static Optional<String> handleEmpty(final Optional<String> value) {
        final Optional<String> trimmed;
        final Optional<String> result;

        trimmed = value.map(StringUtils::trim);
        if (trimmed.orElse("")
            .isEmpty()) {
            result = Optional.empty();
        } else {
            result = value.map(StringUtils::trim);
        }

        return result;
    }

    public record FeeType(Long number, String name, Float amount) {

        public FeeType(final Long number, final String name, final Float amount) {
            Objects.requireNonNull(name);

            this.number = Objects.requireNonNull(number);
            this.name = StringUtils.trim(name);
            this.amount = Objects.requireNonNull(amount);
        }

    }

    public Member deactivated() {
        return new Member(identifier, number, name, birthDate, contactChannels, address, comments, false, false, key,
            feeType, types);
    }

    public Member activated() {
        return new Member(identifier, number, name, birthDate, contactChannels, address, comments, true, true, key,
            feeType, types);
    }

}
