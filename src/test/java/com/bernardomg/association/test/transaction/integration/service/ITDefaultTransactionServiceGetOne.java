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

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.service.DefaultTransactionService;

@IntegrationTest
@DisplayName("Default transaction service - get one")
@Sql({ "/db/queries/transaction/single.sql" })
public class ITDefaultTransactionServiceGetOne {

    @Autowired
    private DefaultTransactionService service;

    public ITDefaultTransactionServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Contains() {
        final Optional<? extends Transaction> result;

        result = service.getOne(1L);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("When reading a single entity with a valid id, an entity is returned")
    public void testGetOne_Existing() {
        final Optional<? extends Transaction> result;

        result = service.getOne(1L);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final Transaction result;
        final Long        id;

        id = 1L;

        result = service.getOne(id)
            .get();

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("Transaction 1", result.getDescription());
        Assertions.assertEquals(1, result.getQuantity());
        Assertions.assertEquals(2, result.getDay());
        Assertions.assertEquals(3, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<? extends Transaction> result;

        result = service.getOne(-1L);

        Assertions.assertFalse(result.isPresent());
    }

}
