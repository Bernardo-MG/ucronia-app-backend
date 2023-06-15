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

package com.bernardomg.association.test.balance.integration.service;

import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.balance.model.MonthlyBalance;
import com.bernardomg.association.balance.service.BalanceService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance with decimals")
public class ITBalanceServiceGetMonthlyBalanceDecimal {

    @Autowired
    private BalanceService service;

    public ITBalanceServiceGetMonthlyBalanceDecimal() {
        super();
    }

    @Test
    @DisplayName("Returns the expected balance when there is a decimal transaction")
    @Sql({ "/db/queries/transaction/decimal.sql" })
    public void testGetMonthlyBalance_Multiple_Data() {
        final MonthlyBalance data;

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(0.12f);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(0.12f);
    }

    @Test
    @DisplayName("Returns the expected balance when the sum of the decimal transactions is 0")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    public void testGetTotalBalance_DecimalsAddUpToZero() {
        final MonthlyBalance data;

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isZero();
        Assertions.assertThat(data.getCumulative())
            .isZero();
    }

}
