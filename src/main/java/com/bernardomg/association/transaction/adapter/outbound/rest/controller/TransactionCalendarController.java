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

package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionDtoMapper;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.usecase.service.TransactionCalendarService;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.TransactionCalendarApi;
import com.bernardomg.ucronia.openapi.model.TransactionCalendarMonthsRangeResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionsResponseDto;

import jakarta.validation.Valid;

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
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    public TransactionsResponseDto getTransactionCalendar(@Valid final List<String> sort, @Valid final Instant from,
            @Valid final Instant to) {
        final Collection<Transaction> transactions;
        final Sorting                 sorting;

        sorting = WebSorting.toSorting(sort);
        transactions = service.getInRange(from, to, sorting);

        return TransactionDtoMapper.toResponseDto(transactions);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    public TransactionCalendarMonthsRangeResponseDto getTransactionCalendarRange() {
        final TransactionCalendarMonthsRange range;

        range = service.getRange();

        return TransactionDtoMapper.toResponseDto(range);
    }

}
