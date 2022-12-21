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

package com.bernardomg.association.test.status.feeyear.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.domain.feeyear.model.FeeYearRange;
import com.bernardomg.association.domain.feeyear.service.FeeYearService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee year service - get all - only active")
public class ITFeeYearServiceGetRangeFilterOnlyActive {

    @Autowired
    private FeeYearService service;

    public ITFeeYearServiceGetRangeFilterOnlyActive() {
        super();
    }

    @Test
    @DisplayName("Returns the range for an active user")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetRange_Active() {
        final FeeYearRange result;

        result = service.getRange(true);

        Assertions.assertEquals(2020, result.getStart());
        Assertions.assertEquals(2020, result.getEnd());
    }

    @Test
    @DisplayName("Returns no range for an inactive user")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    public void testGetRange_Inactive() {
        final FeeYearRange result;

        result = service.getRange(true);

        Assertions.assertEquals(0, result.getStart());
        Assertions.assertEquals(0, result.getEnd());
    }

}
