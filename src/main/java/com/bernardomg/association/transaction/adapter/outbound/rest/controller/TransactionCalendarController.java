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

package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import java.time.YearMonth;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionDtoMapper;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.usecase.service.TransactionCalendarService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.TransactionCalendarApi;
import com.bernardomg.ucronia.openapi.model.TransactionCalendarMonthResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionCalendarMonthsRangeResponseDto;

/**
 * Funds calendar REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class TransactionCalendarController implements TransactionCalendarApi {

    /**
     * Funds calendar service.
     */
    private final TransactionCalendarService service;

    public TransactionCalendarController(final TransactionCalendarService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.CALENDAR)
    public TransactionCalendarMonthResponseDto getTransactionCalendarMonth(final Integer year, final Integer month) {
        final TransactionCalendarMonth calendarMonth;
        final YearMonth                date;

        date = YearMonth.of(year, month);
        calendarMonth = service.getForMonth(date);

        return TransactionDtoMapper.toResponseDto(calendarMonth);
    }

    @Override
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.CALENDAR_RANGE)
    public TransactionCalendarMonthsRangeResponseDto getTransactionCalendarRange() {
        final TransactionCalendarMonthsRange range;

        range = service.getRange();

        return TransactionDtoMapper.toResponseDto(range);
    }

}
