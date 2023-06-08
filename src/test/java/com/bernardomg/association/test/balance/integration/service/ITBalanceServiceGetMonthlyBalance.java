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

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.balance.model.MonthlyBalance;
import com.bernardomg.association.balance.service.BalanceService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance")
public class ITBalanceServiceGetMonthlyBalance {

    @Autowired
    private BalanceService service;

    public ITBalanceServiceGetMonthlyBalance() {
        super();
    }

    @Test
    @DisplayName("Returns all the objects for a full year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetMonthlyBalance_FullYear() {
        final Collection<MonthlyBalance> read;

        read = service.getMonthlyBalance();

        Assertions.assertThat(read)
            .hasSize(12);
    }

    @Test
    @DisplayName("Returns the correct values for a full year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetMonthlyBalance_FullYear_Data() {
        final Iterator<MonthlyBalance> read;
        MonthlyBalance                 data;

        read = service.getMonthlyBalance()
            .iterator();

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 0, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(1);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(2);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 2, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(3);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 3, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(4);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 4, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(5);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 5, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(6);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 6, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(7);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 7, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(8);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 8, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(9);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 9, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(10);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 10, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(11);

        data = read.next();
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 11, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(12);
    }

    @Test
    @DisplayName("Returns a single object for a month with multiple transactions")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetMonthlyBalance_Multiple_count() {
        final Collection<MonthlyBalance> read;

        read = service.getMonthlyBalance();

        Assertions.assertThat(read)
            .hasSize(1);
    }

    @Test
    @DisplayName("Returns the correct values for a month with multiple transactions")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetMonthlyBalance_Multiple_Data() {
        final MonthlyBalance data;

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getTotal())
            .isEqualTo(5);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(5);
    }

}
