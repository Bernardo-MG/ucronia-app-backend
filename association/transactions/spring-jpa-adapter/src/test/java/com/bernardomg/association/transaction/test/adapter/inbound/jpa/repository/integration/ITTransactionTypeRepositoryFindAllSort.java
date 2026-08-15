/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.PropertyReferenceException;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionTypes;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - find all with filter - sort")
@MultipleTransactionTypes
class ITTransactionTypeRepositoryFindAllSort {

    @Autowired
    private TransactionTypeRepository repository;

    @Test
    @DisplayName("With ascending order by description it returns the ordered data")
    void testFindAll_Description_Asc() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(1), TransactionTypes.forNumber(2),
                TransactionTypes.forNumber(3), TransactionTypes.forNumber(4), TransactionTypes.forNumber(5));
    }

    @Test
    @DisplayName("With descending order by description it returns the ordered data")
    void testFindAll_Description_Desc() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.DESC)));

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(5), TransactionTypes.forNumber(4),
                TransactionTypes.forNumber(3), TransactionTypes.forNumber(2), TransactionTypes.forNumber(1));
    }

    @Test
    @DisplayName("Ordering by a not existing field generates an error")
    void testGetAll_NotExisting() {
        final Pagination       pagination;
        final Sorting          sorting;
        final ThrowingCallable executable;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        // WHEN
        executable = () -> repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(PropertyReferenceException.class);
    }

}
