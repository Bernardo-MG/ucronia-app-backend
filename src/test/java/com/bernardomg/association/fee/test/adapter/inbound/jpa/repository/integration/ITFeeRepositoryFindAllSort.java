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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.BadSqlGrammarException;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.test.config.factory.FeesQuery;
import com.bernardomg.association.member.test.config.data.annotation.MultipleMembers;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all - sort")
@MultipleMembers
@MultipleFees
class ITFeeRepositoryFindAllSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    void testFindAll_Date_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(4, Month.MAY), Fees.notPaidAt(5, Month.JUNE));
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    void testFindAll_Date_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidAt(5, Month.JUNE), Fees.paidAt(4, Month.MAY), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(2, Month.MARCH), Fees.paidAt(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    void testFindAll_Name_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "fullName");

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(4, Month.MAY), Fees.notPaidAt(5, Month.JUNE));
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    void testFindAll_Name_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "fullName");

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidAt(5, Month.JUNE), Fees.paidAt(4, Month.MAY), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(2, Month.MARCH), Fees.paidAt(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    @Disabled
    void testFindAll_NotExisting() {
        final FeeQuery         feeQuery;
        final Pageable         pageable;
        final ThrowingCallable executable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "abc");

        feeQuery = FeesQuery.empty();

        // WHEN
        executable = () -> repository.findAll(feeQuery, pageable)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(BadSqlGrammarException.class);
    }

    @Test
    @DisplayName("With ascending order by paid flag it returns the ordered data")
    void testFindAll_Paid_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Sort.by(Order.asc("paid"), Order.asc("date")));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidAt(5, Month.JUNE), Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH),
                Fees.paidAt(3, Month.APRIL), Fees.paidAt(4, Month.MAY));
    }

    @Test
    @DisplayName("With descending order by paid flag it returns the ordered data")
    void testFindAll_Paid_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Sort.by(Order.desc("paid"), Order.asc("date")));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(4, Month.MAY), Fees.notPaidAt(5, Month.JUNE));
    }

}
