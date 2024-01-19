/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.fee.test.service.integration;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.test.config.factory.FeePayments;
import com.bernardomg.association.fee.usecase.FeeService;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.test.data.fee.annotation.PaidFee;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("Fee service - pay fees - validation")
class ITFeeServicePayFeesValidation {

    @Autowired
    private FeeService service;

    public ITFeeServicePayFeesValidation() {
        super();
    }

    @Test
    @DisplayName("With duplicated dates, it throws an exception")
    @ValidMember
    void testCreate_DuplicatedDates() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.duplicated();

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].duplicated", "feeDates[]", "duplicated", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With the fee already paid, it throws an exception")
    @ValidMember
    @PaidFee
    void testCreate_Existing_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.single();

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With the fee already paid, and trying to pay multiple dates, it throws an exception")
    @ValidMember
    @PaidFee
    void testCreate_MultipleDates_OneExisting_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.two();

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

}
