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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoFeeForm;
import com.bernardomg.association.fee.service.DefaultFeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Default fee service - create validation")
@Sql({ "/db/queries/member/single.sql" })
public class ITDefaultFeeCreateValidation {

    @Autowired
    private DefaultFeeService service;

    public ITDefaultFeeCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the member id does not exist")
    public void testCreate_InvalidMember() {
        final DtoFeeForm month;
        final Executable executable;
        final Exception  exception;

        month = new DtoFeeForm();
        month.setMemberId(-1L);
        month.setMonth(2);
        month.setYear(3);
        month.setPaid(true);

        executable = () -> service.create(month);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.member.notExists", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the month is above the range")
    public void testCreate_MonthAboveRange() {
        final DtoFeeForm month;
        final Executable executable;
        final Exception  exception;

        month = new DtoFeeForm();
        month.setMemberId(1L);
        month.setMonth(13);
        month.setYear(2020);
        month.setPaid(true);

        executable = () -> service.create(month);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.fee.invalidMonth", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the month is below the range")
    public void testCreate_MonthBelowRange() {
        final DtoFeeForm month;
        final Executable executable;
        final Exception  exception;

        month = new DtoFeeForm();
        month.setMemberId(1L);
        month.setMonth(0);
        month.setYear(3);
        month.setPaid(true);

        executable = () -> service.create(month);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.fee.invalidMonth", exception.getMessage());
    }

}
