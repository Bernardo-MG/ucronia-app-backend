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

package com.bernardomg.association.test.fee.integration.service;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.request.DtoFeeCreationRequest;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("Fee service - update validation")
public class ITFeeServiceUpdateValidation {

    @Autowired
    private FeeService service;

    public ITFeeServiceUpdateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the member id does not exist")
    @Sql({ "/db/queries/fee/single.sql" })
    @Disabled("This can't happen, it required an inconsistent DB")
    public void testUpdate_InvalidMember() {
        final DtoFeeCreationRequest fee;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        fee = new DtoFeeCreationRequest();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(false);

        executable = () -> service.update(1L, fee);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("notExists", failure.getCode());
        Assertions.assertEquals("memberId", failure.getField());
        Assertions.assertEquals("memberId.notExists", failure.getMessage());
    }

}
