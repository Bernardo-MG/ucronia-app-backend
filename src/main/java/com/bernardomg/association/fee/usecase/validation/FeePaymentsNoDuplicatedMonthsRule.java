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

package com.bernardomg.association.fee.usecase.validation;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fees's months are not duplicated.
 */
public final class FeePaymentsNoDuplicatedMonthsRule implements FieldRule<FeePayments> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeePaymentsNoDuplicatedMonthsRule.class);

    public FeePaymentsNoDuplicatedMonthsRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final FeePayments payments) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final List<YearMonth>        uniqueMonths;
        final long                   duplicates;

        uniqueMonths = payments.months()
            .stream()
            .distinct()
            .sorted()
            .toList();
        if (uniqueMonths.size() < payments.months()
            .size()) {
            // We have repeated dates
            // TODO: is this really an error? It can be corrected easily
            duplicates = (payments.months()
                .size() - uniqueMonths.size());
            log.error("Received {} fee months, but {} are duplicates", payments.months()
                .size(), duplicates);
            // TODO: set duplicates, not number
            fieldFailure = new FieldFailure("duplicated", "months[]", duplicates);
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
