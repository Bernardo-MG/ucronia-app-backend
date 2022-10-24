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

package com.bernardomg.association.test.crud.fee.integration.service;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.crud.fee.model.DtoFeeForm;
import com.bernardomg.association.crud.fee.repository.FeeRepository;
import com.bernardomg.association.crud.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - create errors")
@Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
public class ITFeeServiceCreateError {

    @Autowired
    private FeeRepository repository;

    @Autowired
    private FeeService    service;

    public ITFeeServiceCreateError() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when there is an entry for that member and date")
    public void testCreate_ExistingDateAndMember() {
        final DtoFeeForm fee;
        final Executable executable;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 1));
        fee.setPaid(true);

        executable = () -> {
            service.create(fee);
            repository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    @DisplayName("Throws an exception when there is an entry for that member and date, ignoring the day")
    public void testCreate_ExistingDateAndMember_ChangesDay() {
        final DtoFeeForm fee;
        final Executable executable;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2020, 1, 2));
        fee.setPaid(true);

        executable = () -> {
            service.create(fee);
            repository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    @DisplayName("Throws an exception when the date is missing")
    public void testCreate_MissingDate() {
        final DtoFeeForm fee;
        final Executable executable;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(null);
        fee.setPaid(true);

        executable = () -> {
            service.create(fee);
            repository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    @DisplayName("Throws an exception when the paid flag is missing")
    public void testCreate_MissingPaid() {
        final DtoFeeForm fee;
        final Executable executable;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);
        fee.setDate(new GregorianCalendar(2021, 1, 1));
        fee.setPaid(null);

        executable = () -> {
            service.create(fee);
            repository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, executable);
    }

}
