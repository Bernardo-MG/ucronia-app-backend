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

package com.bernardomg.association.test.transaction.integration.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.factory.ModelFactory;
import com.bernardomg.association.transaction.repository.TransactionRepository;

@IntegrationTest
@DisplayName("Transaction repository - sum all with decimals")
public class ITTransactionRepositorySumAllDecimal {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositorySumAllDecimal() {
        super();
    }

    @Test
    @DisplayName("Returns the expected sum when there is a decimal transaction")
    @Sql({ "/db/queries/transaction/decimal.sql" })
    public void testGetBalance_Decimal() {
        final Float sum;

        sum = repository.sumAll();

        Assertions.assertEquals(Double.valueOf(0.12)
            .floatValue(), sum);
    }

    @Test
    @DisplayName("Returns the expected sum when the sum of the decimal transactions is 0")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    public void testGetBalance_DecimalsAddUpToZero() {
        final Float sum;

        sum = repository.sumAll();

        Assertions.assertEquals(Double.valueOf(0)
            .floatValue(), sum);
    }

    @Test
    @DisplayName("Returns the expected sum when the sum of the decimal transactions is 0, after being saved into the repository")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    public void testGetBalance_SaveAndRead_DecimalsAddUpToZero() {
        final Float sum;

        repository.save(ModelFactory.transaction(-40.8f));
        repository.save(ModelFactory.transaction(13.6f));
        repository.save(ModelFactory.transaction(13.6f));
        repository.save(ModelFactory.transaction(13.6f));

        sum = repository.sumAll();

        Assertions.assertEquals(Double.valueOf(0)
            .floatValue(), sum);
    }

}
