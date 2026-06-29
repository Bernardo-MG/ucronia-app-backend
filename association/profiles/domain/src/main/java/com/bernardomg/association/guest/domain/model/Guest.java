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

import com.bernardomg.association.profile.domain.model.ContactChannel;
import com.bernardomg.association.profile.domain.model.Name;

public record Guest(Optional<String> identifier, Long number, Name name, Optional<Instant> birthDate,
        Collection<ContactChannel> contactChannels, Collection<Instant> games, Optional<String> address,
        Optional<String> comments, Set<String> types) {

    public static final String PROFILE_TYPE = "guest";

    public Guest(final Optional<String> identifier, final Long number, final Name name,
            final Optional<Instant> birthDate, final Collection<ContactChannel> contactChannels,
            final Collection<Instant> games, final Optional<String> address, final Optional<String> comments,
            final Set<String> types) {
        Objects.requireNonNull(identifier);
        Objects.requireNonNull(address);
        Objects.requireNonNull(comments);
        Objects.requireNonNull(contactChannels);
        Objects.requireNonNull(games);
        Objects.requireNonNull(types);

        this.identifier = handleEmpty(identifier);
        this.number = Objects.requireNonNull(number);
        this.name = Objects.requireNonNull(name);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.contactChannels = List.copyOf(contactChannels);
        this.games = List.copyOf(games);
        this.address = handleEmpty(address);
        this.comments = handleEmpty(comments);
        this.types = Set.copyOf(types);
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

}
