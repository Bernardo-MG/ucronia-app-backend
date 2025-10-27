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
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionDtoMapper;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.usecase.service.TransactionService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.TransactionApi;
import com.bernardomg.ucronia.openapi.model.TransactionChangeDto;
import com.bernardomg.ucronia.openapi.model.TransactionCreationDto;
import com.bernardomg.ucronia.openapi.model.TransactionPageResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Transaction REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class TransactionController implements TransactionApi {

    /**
     * Transaction service.
     */
    private final TransactionService service;

    public TransactionController(final TransactionService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = TransactionCaches.TRANSACTION, key = "#result.content.index") },
            evict = { @CacheEvict(cacheNames = {
                    // Transaction caches
                    TransactionCaches.TRANSACTIONS,
                    // Calendar caches
                    TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Balance caches
                    TransactionCaches.BALANCE, TransactionCaches.MONTHLY_BALANCE,
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public TransactionResponseDto createTransaction(@Valid final TransactionCreationDto transactionCreationDto) {
        final Transaction transaction;
        final Transaction toCreate;

        toCreate = TransactionDtoMapper.toDomain(transactionCreationDto);
        transaction = service.create(toCreate);

        return TransactionDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { TransactionCaches.TRANSACTION }), @CacheEvict(cacheNames = {
            // Transaction caches
            TransactionCaches.TRANSACTIONS,
            // Calendar caches
            TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
            // Balance caches
            TransactionCaches.BALANCE, TransactionCaches.MONTHLY_BALANCE,
            // Fee caches
            FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public TransactionResponseDto deleteTransaction(final Long index) {
        final Transaction transaction;

        transaction = service.delete(index);

        return TransactionDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.TRANSACTIONS)
    public TransactionPageResponseDto getAllTransactions(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort, @Valid final Instant date,
            @Valid final Instant from, @Valid final Instant to) {
        final TransactionQuery  query;
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<Transaction> transactions;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        query = new TransactionQuery(date, from, to);
        transactions = service.getAll(query, pagination, sorting);

        return TransactionDtoMapper.toResponseDto(transactions);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = TransactionCaches.TRANSACTION)
    public TransactionResponseDto getOneTransaction(final Long index) {
        final Optional<Transaction> transaction;

        transaction = service.getOne(index);

        return TransactionDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = TransactionCaches.TRANSACTION, key = "#result.content.index") },
            evict = { @CacheEvict(cacheNames = {
                    // Transaction caches
                    TransactionCaches.TRANSACTIONS,
                    // Calendar caches
                    TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Balance caches
                    TransactionCaches.BALANCE, TransactionCaches.MONTHLY_BALANCE,
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public TransactionResponseDto updateTransaction(final Long index,
            @Valid final TransactionChangeDto transactionChangeDto) {
        final Transaction transaction;
        final Transaction updated;

        transaction = TransactionDtoMapper.toDomain(index, transactionChangeDto);
        updated = service.update(transaction);
        return TransactionDtoMapper.toResponseDto(updated);
    }

}
