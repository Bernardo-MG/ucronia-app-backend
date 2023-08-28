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

package com.bernardomg.association.calendar.transaction.controller;

import java.time.YearMonth;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.calendar.transaction.service.TransactionCalendarService;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.security.permission.authorization.AuthorizedResource;
import com.bernardomg.security.permission.constant.Actions;

import lombok.AllArgsConstructor;

/**
 * Transaction REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionCalendarController {

    /**
     * Transaction service.
     */
    private final TransactionCalendarService service;

    @GetMapping(path = "/{year}/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "FEE", action = Actions.READ)
    public Iterable<? extends Transaction> readAll(@PathVariable("year") final Integer year,
            @PathVariable("year") final Integer month) {
        final YearMonth date;

        date = YearMonth.of(year, month);
        return service.getYearMonth(date);
    }

    @GetMapping(path = "/range", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "TRANSACTION", action = Actions.READ)
    public TransactionRange readRange() {
        return service.getRange();
    }

}
