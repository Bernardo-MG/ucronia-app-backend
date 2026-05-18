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

package com.bernardomg.association.fee.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.bernardomg.association.fee.domain.model.FeeMember.Name;

public record MemberFees(Member member, Collection<Fee> fees) {

    public MemberFees(final Member member, final Collection<Fee> fees) {
        Objects.requireNonNull(fees);

        this.member = Objects.requireNonNull(member);
        this.fees = List.copyOf(fees);
    }

    public record Fee(Instant month, Boolean paid) {

        public Fee(final Instant month, final Boolean paid) {
            // TODO: Ensure it is at the beginning of the month
            this.month = Objects.requireNonNull(month);
            this.paid = Objects.requireNonNull(paid);
        }

    }

    public static record Member(Long number, Name name, Boolean active) {

        public Member(final Long number, final Name name, final Boolean active) {
            this.number = Objects.requireNonNull(number);
            this.name = Objects.requireNonNull(name);
            this.active = Objects.requireNonNull(active);
        }

    }

}
