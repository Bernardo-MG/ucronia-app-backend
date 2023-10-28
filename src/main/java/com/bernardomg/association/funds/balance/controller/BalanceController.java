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

package com.bernardomg.association.funds.balance.controller;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.request.ValidatedBalanceQuery;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.cache.FundsCaches;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/funds/balance")
@AllArgsConstructor
@Transactional
public class BalanceController {

    private final BalanceService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = FundsCaches.BALANCE)
    public MonthlyBalance readBalance() {
        return service.getBalance();
    }

    @GetMapping(path = "/monthly", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "BALANCE", action = Actions.READ)
    @Cacheable(cacheNames = FundsCaches.MONTHLY_BALANCE)
    public Collection<? extends MonthlyBalance> readMonthlyBalance(@Valid final ValidatedBalanceQuery query,
            final Sort sort) {
        return service.getMonthlyBalance(query, sort);
    }

}
