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
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactName;

public record Guest(String identifier, Long number, ContactName name, Instant birthDate,
        Collection<ContactChannel> contactChannels, Collection<Instant> games, String comments, Set<String> types) {

    public Guest(final String identifier, final Long number, final ContactName name, final Instant birthDate,
            final Collection<ContactChannel> contactChannels, final Collection<Instant> games, final String comments,
            final Set<String> types) {
        this.identifier = identifier;
        this.number = number;
        this.name = name;
        this.birthDate = birthDate;
        this.contactChannels = contactChannels;
        this.games = games;
        this.comments = StringUtils.trim(comments);
        this.types = types;
    }

}
