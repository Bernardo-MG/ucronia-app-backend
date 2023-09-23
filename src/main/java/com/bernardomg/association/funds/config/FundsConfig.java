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

package com.bernardomg.association.funds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.balance.service.DefaultBalanceService;
import com.bernardomg.association.funds.calendar.service.DefaultTransactionCalendarService;
import com.bernardomg.association.funds.calendar.service.TransactionCalendarService;
import com.bernardomg.association.funds.transaction.model.mapper.TransactionMapper;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.service.DefaultTransactionService;
import com.bernardomg.association.funds.transaction.service.TransactionService;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class FundsConfig {

    public FundsConfig() {
        super();
    }

    @Bean("balanceService")
    public BalanceService getBalanceService(final MonthlyBalanceRepository monthlyBalanceRepository) {
        return new DefaultBalanceService(monthlyBalanceRepository);
    }

    @Bean("transactionCalendarService")
    public TransactionCalendarService getTransactionCalendarService(final TransactionRepository transactionRepository,
            final TransactionMapper mapper) {
        return new DefaultTransactionCalendarService(transactionRepository, mapper);
    }

    @Bean("transactionService")
    public TransactionService getTransactionService(final TransactionRepository transactionRepository,
            final TransactionMapper mapper) {
        return new DefaultTransactionService(transactionRepository, mapper);
    }

}
