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

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.cache.TransactionCaches;
import com.bernardomg.association.transaction.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.service.TransactionBalanceService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Balance REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/funds/balance")
@AllArgsConstructor
@Transactional
public class BalanceController {

    /**
     * Balance service
     */
    private final TransactionBalanceService service;

    /**
     * Returns the current balance.
     *
     * @return the current balance
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.BALANCE)
    public TransactionCurrentBalance readBalance() {
        return service.getBalance();
    }

    /**
     * Returns the monthly balance.
     *
     * @param balance
     *            query to filter balances
     * @param sort
     *            sorting to apply
     * @return the monthly balance
     */
    @GetMapping(path = "/monthly", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.MONTHLY_BALANCE)
    public Collection<? extends TransactionMonthlyBalance>
            readMonthlyBalance(@Valid final TransactionBalanceQuery balance, final Sort sort) {
        return service.getMonthlyBalance(balance, sort);
    }

}
