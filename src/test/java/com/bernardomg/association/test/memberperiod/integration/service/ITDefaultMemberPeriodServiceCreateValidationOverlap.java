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

package com.bernardomg.association.test.memberperiod.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.memberperiod.model.DtoMemberPeriod;
import com.bernardomg.association.memberperiod.service.DefaultMemberPeriodService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Default member period service - create overlap validation")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member_period/single.sql" })
public class ITDefaultMemberPeriodServiceCreateValidationOverlap {

    @Autowired
    private DefaultMemberPeriodService service;

    public ITDefaultMemberPeriodServiceCreateValidationOverlap() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the period matches the end month")
    public void testCreate_MatchEndMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(4);
        period.setStartYear(5);
        period.setEndMonth(4);
        period.setEndYear(5);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period matches the starting month")
    public void testCreate_MatchStartMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(2);
        period.setStartYear(3);
        period.setEndMonth(2);
        period.setEndYear(3);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period overlaps the end month of an existing period")
    public void testCreate_OverlapsEndMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(3);
        period.setStartYear(5);
        period.setEndMonth(5);
        period.setEndYear(5);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period overlaps an existing period")
    public void testCreate_OverlapsFullPeriod() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(1);
        period.setStartYear(3);
        period.setEndMonth(5);
        period.setEndYear(5);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period overlaps the starting month of an existing period")
    public void testCreate_OverlapsStartMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(1);
        period.setStartYear(3);
        period.setEndMonth(3);
        period.setEndYear(3);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period touches the end month")
    public void testCreate_TouchEndMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(4);
        period.setStartYear(5);
        period.setEndMonth(5);
        period.setEndYear(5);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the period touches the starting month")
    public void testCreate_TouchStartMonth() {
        final DtoMemberPeriod period;
        final Executable      executable;
        final Exception       exception;

        period = new DtoMemberPeriod();
        period.setMember(1L);
        period.setStartMonth(1);
        period.setStartYear(3);
        period.setEndMonth(2);
        period.setEndYear(3);

        executable = () -> service.create(1L, period);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.memberPeriod.overlapsExisting", exception.getMessage());
    }

}
