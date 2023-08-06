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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.fee.util.model.FeesCreate;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee service - create errors")
class ITFeeServiceCreateError {

    @Autowired
    private FeeRepository repository;

    @Autowired
    private FeeService    service;

    public ITFeeServiceCreateError() {
        super();
    }

    @Test
    @DisplayName("With a repeated member and month it throws an exception")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testCreate_ExistingDateAndMember() {
        final FeeCreate        feeRequest;
        final ThrowingCallable execution;

        feeRequest = FeesCreate.valid();

        execution = () -> {
            service.create(feeRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a repeated member and month, but with another day, it throws an exception")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testCreate_ExistingDateAndMember_ChangesDay() {
        final FeeCreate        feeRequest;
        final ThrowingCallable executable;

        feeRequest = FeesCreate.valid();

        executable = () -> {
            service.create(feeRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

}
