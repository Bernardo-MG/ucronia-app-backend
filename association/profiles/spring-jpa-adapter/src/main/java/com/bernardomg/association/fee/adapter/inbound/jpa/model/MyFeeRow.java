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

package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.time.Instant;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee.Transaction;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.association.profile.domain.model.Name;

public record MyFeeRow(Instant month, Boolean paid, Long number, String firstName, String lastName, String nickname,
        Long transactionIndex, Instant transactionDate) {

    public MyFee toDomain() {
        final boolean               paidFee     = Boolean.TRUE.equals(paid);
        final FeeMember             member      = new FeeMember(number,
            new Name(firstName, lastName, Optional.ofNullable(nickname)));
        final Optional<Transaction> transaction = paidFee && (transactionIndex != null)
                ? Optional.of(new Transaction(transactionIndex, transactionDate))
                : Optional.empty();

        return new MyFee(month, paidFee, member, transaction);
    }

}
