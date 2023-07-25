/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.fee.service.integration;

import java.util.GregorianCalendar;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.request.ValidatedFeeCreate;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee service - create validation")
@Sql({ "/db/queries/member/single.sql" })
class ITFeeServiceCreateValidation {

    @Autowired
    private FeeService service;

    public ITFeeServiceCreateValidation() {
        super();
    }

    private final ValidatedFeeCreate getInvalidIdFeeCreate() {
        return ValidatedFeeCreate.builder()
            .memberId(-1L)
            .date(new GregorianCalendar(2020, 1, 2))
            .paid(true)
            .build();
    }

    @Test
    @DisplayName("With a missing id it throws an exception")
    void testCreate_InvalidMember() {
        final ValidatedFeeCreate feeRequest;
        final ThrowingCallable   execution;
        final FieldFailure       failure;

        feeRequest = getInvalidIdFeeCreate();

        execution = () -> service.create(feeRequest);

        failure = FieldFailure.of("memberId.notExists", "memberId", "notExists", -1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

}
