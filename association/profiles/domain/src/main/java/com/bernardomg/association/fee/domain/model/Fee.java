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
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.security.domain.audit.model.AuditDetails;

public record Fee(Instant month, Boolean paid, FeeMember member, FeeType feeType, Optional<Transaction> transaction,
        AuditDetails audit) {

    public Fee(final Instant month, final Boolean paid, final FeeMember member, final FeeType feeType,
            final Optional<Transaction> transaction) {
        this(month, paid, member, feeType, transaction, new AuditDetails());
    }

    public Fee(final Instant month, final Boolean paid, final FeeMember member, final FeeType feeType,
            final Optional<Transaction> transaction, final AuditDetails audit) {
        Objects.requireNonNull(month, "Month can't be null");
        Objects.requireNonNull(paid, "Paid flag can't be null");
        Objects.requireNonNull(member, "Member can't be null");
        Objects.requireNonNull(feeType, "Fee type can't be null");
        Objects.requireNonNull(transaction, "Transaction can't be null");
        Objects.requireNonNull(audit, "Audit can't be null");

        // TODO: Ensure it is at the beginning of the month
        this.month = month;
        this.paid = paid;
        this.member = member;
        this.feeType = feeType;
        this.transaction = transaction;
        this.audit = audit;
    }

    public static Fee unpaid(final Instant month, final Long number, final Name name, final FeeType feeType) {
        final FeeMember member;

        member = new FeeMember(number, name);
        return new Fee(month, false, member, feeType, Optional.empty(), new AuditDetails());
    }

    public static Fee paid(final Instant month, final Long number, final Name name, final FeeType feeType,
            final Transaction transaction) {
        final FeeMember member;

        member = new FeeMember(number, name);
        return new Fee(month, true, member, feeType, Optional.of(transaction), new AuditDetails());
    }

    public static Fee paid(final Instant month, final Long number, final Name name, final FeeType feeType,
            final Transaction transaction, final AuditDetails audit) {
        final FeeMember member;

        member = new FeeMember(number, name);
        return new Fee(month, true, member, feeType, Optional.of(transaction), audit);
    }

    public static Fee paid(final Instant month, final Long number, final Name name, final FeeType feeType) {
        final FeeMember member;

        member = new FeeMember(number, name);
        return new Fee(month, true, member, feeType, Optional.empty(), new AuditDetails());
    }

    public static Fee paid(final Instant month, final Long number, final Name name, final FeeType feeType,
            final AuditDetails audit) {
        final FeeMember member;

        member = new FeeMember(number, name);
        return new Fee(month, true, member, feeType, Optional.empty(), audit);
    }

    public static record Transaction(Long index, Instant date) {

        public Transaction(final Long index, final Instant date) {
            Objects.requireNonNull(index, "Index can't be null");
            Objects.requireNonNull(date, "Date can't be null");

            this.index = index;
            this.date = date;
        }
    }

}
