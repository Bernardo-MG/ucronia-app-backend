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
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;

public record MemberProfile(String identifier, Long number, ProfileName name, Instant birthDate,
        Collection<ContactChannel> contactChannels, String address, String comments, Boolean active, Boolean renew,
        FeeType feeType, Set<String> types) {

    public MemberProfile(final String identifier, final Long number, final ProfileName name, final Instant birthDate,
            final Collection<ContactChannel> contactChannels, final String address, final String comments,
            final Boolean active, final Boolean renew, final FeeType feeType, final Set<String> types) {
        this.identifier = identifier;
        this.number = number;
        this.name = name;
        this.birthDate = birthDate;
        this.contactChannels = List.copyOf(contactChannels);
        this.address = StringUtils.trim(address);
        this.comments = StringUtils.trim(comments);
        this.active = active;
        this.renew = renew;
        this.feeType = feeType;
        this.types = Set.copyOf(types);
    }

    public record FeeType(long number) {}

}
