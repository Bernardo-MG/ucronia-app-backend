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

package com.bernardomg.association.guest.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public record Guest(String identifier, Long number, Name name, Optional<Instant> birthDate,
        Collection<ContactChannel> contactChannels, Collection<Instant> games, String address, String comments,
        Set<String> types) {

    public static final String PROFILE_TYPE = "guest";

    public Guest(final String identifier, final Long number, final Name name, final Optional<Instant> birthDate,
            final Collection<ContactChannel> contactChannels, final Collection<Instant> games, final String address,
            final String comments, final Set<String> types) {
        Objects.requireNonNull(contactChannels);
        Objects.requireNonNull(games);
        Objects.requireNonNull(address);
        Objects.requireNonNull(comments);
        Objects.requireNonNull(types);

        this.identifier = Objects.requireNonNull(identifier);
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.contactChannels = List.copyOf(contactChannels);
        this.games = List.copyOf(games);
        this.address = StringUtils.trim(address);
        this.comments = StringUtils.trim(comments);
        this.types = Set.copyOf(types);
    }

    public record ContactChannel(ContactMethod contactMethod, String detail) {

        public ContactChannel(final ContactMethod contactMethod, final String detail) {
            Objects.requireNonNull(detail);

            this.contactMethod = Objects.requireNonNull(contactMethod);
            this.detail = StringUtils.trim(detail);
        }

    }

    public record ContactMethod(Long number, String name) {

        public ContactMethod(final Long number, final String name) {
            Objects.requireNonNull(name);

            this.number = Objects.requireNonNull(number);
            this.name = StringUtils.trim(name);
        }

    }

    public record Name(String firstName, String lastName) {

        public Name(final String firstName, final String lastName) {
            Objects.requireNonNull(firstName);
            Objects.requireNonNull(lastName);

            this.firstName = StringUtils.trim(firstName);
            this.lastName = StringUtils.trim(lastName);
        }

        public final String fullName() {
            return String.format("%s %s", firstName, lastName)
                .trim();
        }

    }

}
