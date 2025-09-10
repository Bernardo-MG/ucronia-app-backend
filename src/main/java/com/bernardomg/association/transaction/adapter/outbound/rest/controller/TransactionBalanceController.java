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
import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionDtoMapper;
import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.usecase.service.TransactionBalanceService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.TransactionBalanceApi;
import com.bernardomg.ucronia.openapi.model.TransactionCurrentBalanceResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionMonthlyBalanceResponseDto;

import jakarta.validation.Valid;

/**
 * Balance REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class TransactionBalanceController implements TransactionBalanceApi {

    /**
     * Balance service
     */
    private final TransactionBalanceService service;

    public TransactionBalanceController(final TransactionBalanceService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.BALANCE)
    public TransactionCurrentBalanceResponseDto getCurrentTransactionBalance() {
        final TransactionCurrentBalance balance;

        balance = service.getBalance();

        return TransactionDtoMapper.toResponseDto(balance);
    }

    @Override
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.MONTHLY_BALANCE)
    public TransactionMonthlyBalanceResponseDto getMonthlyTransactionBalance(@Valid final YearMonth startDate,
            @Valid final YearMonth endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the monthly balance.
     *
     * @param balance
     *            query to filter balances
     * @return the monthly balance
     */
    @GetMapping(path = "/monthly", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.MONTHLY_BALANCE)
    public Collection<? extends TransactionMonthlyBalance>
            readMonthlyBalance(@Valid final TransactionBalanceQuery balance) {
        return service.getMonthlyBalance(balance);
    }

}
