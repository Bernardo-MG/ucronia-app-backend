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

package com.bernardomg.association.test.transaction.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.service.DefaultTransactionService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Default transaction service - create validation")
public class ITDefaultTransactionServiceCreateValidation {

    @Autowired
    private DefaultTransactionService service;

    public ITDefaultTransactionServiceCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the month is above limits")
    public void testCreate_MonthAboveLimit() {
        final DtoTransaction transaction;
        final Executable     executable;
        final Exception      exception;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setQuantity(1l);
        transaction.setDay(2);
        transaction.setMonth(13);
        transaction.setYear(4);

        executable = () -> service.create(transaction);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.transaction.invalidMonth", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the month is below limits")
    public void testCreate_MonthBelowLimit() {
        final DtoTransaction transaction;
        final Executable     executable;
        final Exception      exception;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setQuantity(1l);
        transaction.setDay(2);
        transaction.setMonth(0);
        transaction.setYear(4);

        executable = () -> service.create(transaction);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.transaction.invalidMonth", exception.getMessage());
    }

}
