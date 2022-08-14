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

package com.bernardomg.association.test.member.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMemberPeriod;
import com.bernardomg.association.member.service.DefaultMemberPeriodService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Default member period service - update validation")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member_period/single.sql" })
public class ITDefaultMemberPeriodServiceUpdateValidation {

    @Autowired
    private DefaultMemberPeriodService service;

    public ITDefaultMemberPeriodServiceUpdateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the start month is after the end month")
    public void testUpdate_StartMonthAfterEndMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setStartMonth(4);
        period.setStartYear(1);
        period.setEndMonth(2);
        period.setEndYear(1);

        executable = () -> service.update(1L, 1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.startMonthAfterEndMonth", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the start year is after the end year")
    public void testUpdate_StartYearAfterEndYear() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setStartMonth(3);
        period.setStartYear(2);
        period.setEndMonth(4);
        period.setEndYear(1);

        executable = () -> service.update(1L, 1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.startYearAfterEndYear", exception.getMessage());
    }

}
