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

package com.bernardomg.association.transaction.controller;

import java.time.YearMonth;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.cache.TransactionCaches;
import com.bernardomg.association.transaction.model.CalendarFundsDate;
import com.bernardomg.association.transaction.model.MonthsRange;
import com.bernardomg.association.transaction.service.FundsCalendarService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import lombok.AllArgsConstructor;

/**
 * Funds calendar REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/funds/calendar")
@AllArgsConstructor
@Transactional
public class FundsCalendarController {

    /**
     * Funds calendar service.
     */
    private final FundsCalendarService service;

    /**
     * Returns all the fund changes for a month.
     *
     * @param year
     *            year to read
     * @param month
     *            month to read
     * @return all the fund changes for the month
     */
    @GetMapping(path = "/{year}/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.CALENDAR)
    public Iterable<? extends CalendarFundsDate> readMonth(@PathVariable("year") final Integer year,
            @PathVariable("month") final Integer month) {
        final YearMonth date;

        date = YearMonth.of(year, month);
        return service.getYearMonth(date);
    }

    /**
     * Returns the range of available months.
     *
     * @return the range of available months
     */
    @GetMapping(path = "/range", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.CALENDAR_RANGE)
    public MonthsRange readRange() {
        return service.getRange();
    }

}
