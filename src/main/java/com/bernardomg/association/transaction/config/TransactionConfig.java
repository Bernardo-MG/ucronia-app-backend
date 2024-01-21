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

package com.bernardomg.association.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.inbound.jpa.repository.JpaTransactionBalanceRepository;
import com.bernardomg.association.transaction.inbound.jpa.repository.JpaTransactionRepository;
import com.bernardomg.association.transaction.inbound.jpa.repository.MonthlyBalanceSpringRepository;
import com.bernardomg.association.transaction.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionBalanceService;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionCalendarService;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.association.transaction.usecase.service.TransactionBalanceService;
import com.bernardomg.association.transaction.usecase.service.TransactionCalendarService;
import com.bernardomg.association.transaction.usecase.service.TransactionService;

/**
 * Transaction configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class TransactionConfig {

    public TransactionConfig() {
        super();
    }

    @Bean("balanceService")
    public TransactionBalanceService
            getBalanceService(final TransactionBalanceRepository transactionBalanceRepository) {
        return new DefaultTransactionBalanceService(transactionBalanceRepository);
    }

    @Bean("fundsCalendarService")
    public TransactionCalendarService getFundsCalendarService(final TransactionRepository transactionRepository) {
        return new DefaultTransactionCalendarService(transactionRepository);
    }

    @Bean("transactionBalanceRepository")
    public TransactionBalanceRepository
            getTransactionBalanceRepository(final MonthlyBalanceSpringRepository monthlyBalanceRepository) {
        return new JpaTransactionBalanceRepository(monthlyBalanceRepository);
    }

    @Bean("transactionRepository")
    public TransactionRepository getTransactionRepository(final TransactionSpringRepository transactionRepository) {
        return new JpaTransactionRepository(transactionRepository);
    }

    @Bean("transactionService")
    public TransactionService getTransactionService(final TransactionRepository transactionRepository) {
        return new DefaultTransactionService(transactionRepository);
    }

}
