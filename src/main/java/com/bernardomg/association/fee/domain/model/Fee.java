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
import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.profile.domain.model.ProfileName;

public record Fee(YearMonth month, Boolean paid, Member member, FeeType feeType, Optional<Transaction> transaction) {

    public static Fee unpaid(final YearMonth month, final Long number, final ProfileName name, final FeeType feeType) {
        final Member member;

        member = new Fee.Member(number, name);
        return new Fee(month, false, member, feeType, Optional.empty());
    }

    public static Fee paid(final YearMonth month, final Long number, final ProfileName name, final FeeType feeType,
            final Transaction transaction) {
        final Member member;

        member = new Fee.Member(number, name);
        return new Fee(month, true, member, feeType, Optional.of(transaction));
    }

    public static Fee paid(final YearMonth month, final Long number, final ProfileName name, final FeeType feeType) {
        final Member member;

        member = new Fee.Member(number, name);
        return new Fee(month, true, member, feeType, Optional.empty());
    }

    public static record Member(Long number, ProfileName name) {}

    public static record Transaction(Instant date, Long index) {}

    public static record FeeType(Long number, String name, Float amount) {}

}
