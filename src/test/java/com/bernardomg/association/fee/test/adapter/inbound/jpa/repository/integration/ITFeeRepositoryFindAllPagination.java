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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.factory.FeesQuery;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleInactiveMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@DisplayName("FeeRepository - find all - pagination")
@MultipleInactiveMembershipPerson
@MultipleFees
class ITFeeRepositoryFindAllPagination extends AbstractPaginationIT<Fee> {

    @Autowired
    private FeeRepository repository;

    public ITFeeRepositoryFindAllPagination() {
        super(5);
    }

    @Override
    protected Iterable<Fee> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(FeesQuery.empty(), pagination, sorting);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testFindAll_Page1() {
        final FeeQuery      feeQuery;
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testFindAll_Page2() {
        final FeeQuery      feeQuery;
        final Iterable<Fee> fees;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.unsorted();

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(2, Month.MARCH));
    }

}
