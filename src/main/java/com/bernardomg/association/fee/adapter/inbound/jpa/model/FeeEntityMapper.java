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

import java.time.YearMonth;
import java.time.ZoneOffset;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.Fee.FeeType;
import com.bernardomg.association.fee.domain.model.Fee.Transaction;
import com.bernardomg.association.profile.domain.model.ProfileName;

/**
 * Fee repository mapper.
 */
public final class FeeEntityMapper {

    public static final Fee toDomain(final FeeEntity entity) {
        final Transaction transaction;
        final ProfileName name;
        final YearMonth   date;
        final Fee         fee;
        final FeeType     feeType;

        name = new ProfileName(entity.getMember()
            .getProfile()
            .getFirstName(),
            entity.getMember()
                .getProfile()
                .getLastName());

        date = YearMonth.from(entity.getMonth()
            .atZone(ZoneOffset.UTC));
        if (entity.getPaid()) {
            feeType = new Fee.FeeType(entity.getFeeType()
                .getNumber());
            if (entity.getTransaction() == null) {
                fee = Fee.paid(date, entity.getMember()
                    .getProfile()
                    .getNumber(), name, feeType);
            } else {
                transaction = new Fee.Transaction(entity.getTransaction()
                    .getDate(),
                    entity.getTransaction()
                        .getIndex());
                fee = Fee.paid(date, entity.getMember()
                    .getProfile()
                    .getNumber(), name, feeType, transaction);
            }
        } else {
            feeType = new Fee.FeeType(entity.getFeeType()
                .getNumber());
            fee = Fee.unpaid(date, entity.getMember()
                .getProfile()
                .getNumber(), name, feeType);
        }

        return fee;
    }

    private FeeEntityMapper() {
        super();
    }

}
