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

package com.bernardomg.association.test.paidmonth.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.paidmonth.model.DtoPaidMonth;
import com.bernardomg.association.paidmonth.service.DefaultPaidMonthService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Default paid month service - create in period validation")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member_period/single.sql" })
public class ITDefaultPaidMonthCreateValidationPeriod {

    @Autowired
    private DefaultPaidMonthService service;

    public ITDefaultPaidMonthCreateValidationPeriod() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the month is after the period")
    public void testCreate_AfterPeriod() {
        final DtoPaidMonth month;
        final Executable   executable;
        final Exception    exception;

        month = new DtoPaidMonth();
        month.setMember(1L);
        month.setMonth(1);
        month.setYear(3);
        month.setPaid(true);

        executable = () -> service.create(1L, month);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.paidMonth.outOfPeriod", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the month is before the period")
    public void testCreate_BeforePeriod() {
        final DtoPaidMonth month;
        final Executable   executable;
        final Exception    exception;

        month = new DtoPaidMonth();
        month.setMember(1L);
        month.setMonth(1);
        month.setYear(3);
        month.setPaid(true);

        executable = () -> service.create(1L, month);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.paidMonth.outOfPeriod", exception.getMessage());
    }

}
