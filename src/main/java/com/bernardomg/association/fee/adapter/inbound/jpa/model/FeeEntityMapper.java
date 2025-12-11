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
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.fee.domain.model.Fee;

/**
 * Fee repository mapper.
 */
public final class FeeEntityMapper {

    public static final Fee toDomain(final FeeEntity entity) {
        final Fee.Member                member;
        final Optional<Fee.Transaction> transaction;
        final ContactName               name;
        final YearMonth                 date;

        name = new ContactName(entity.getMember()
            .getFirstName(),
            entity.getMember()
                .getLastName());
        member = new Fee.Member(entity.getMember()
            .getNumber(), name);

        if (entity.getPaid()) {
            transaction = Optional.of(new Fee.Transaction(entity.getTransaction()
                .getDate(),
                entity.getTransaction()
                    .getIndex()));
        } else {
            transaction = Optional.empty();
        }
        date = YearMonth.from(entity.getDate()
            .atZone(ZoneOffset.UTC));
        return new Fee(date, entity.getPaid(), member, transaction);
    }

    private FeeEntityMapper() {
        super();
    }

}
