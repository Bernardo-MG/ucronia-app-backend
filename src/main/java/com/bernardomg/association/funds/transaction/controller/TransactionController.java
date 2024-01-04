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

package com.bernardomg.association.funds.transaction.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.funds.cache.FundsCaches;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.TransactionChange;
import com.bernardomg.association.funds.transaction.model.TransactionQuery;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.association.membership.fee.cache.FeeCaches;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Transaction REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/funds/transaction")
@AllArgsConstructor
@Transactional
public class TransactionController {

    /**
     * Transaction service.
     */
    private final TransactionService service;

    /**
     * Creates a transaction.
     *
     * @param transaction
     *            transaction to add
     * @return the new transaction
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = FundsCaches.TRANSACTION, key = "#result.index") },
            evict = { @CacheEvict(cacheNames = {
                    // Transaction caches
                    FundsCaches.TRANSACTIONS,
                    // Calendar caches
                    FundsCaches.CALENDAR, FundsCaches.CALENDAR_RANGE,
                    // Balance caches
                    FundsCaches.BALANCE, FundsCaches.MONTHLY_BALANCE,
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public Transaction create(@Valid @RequestBody final TransactionChange transaction) {
        return service.create(transaction);
    }

    /**
     * Deletes a transaction by its id.
     *
     * @param index
     *            transaction index
     */
    @DeleteMapping(path = "/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { FundsCaches.TRANSACTION }), @CacheEvict(cacheNames = {
            // Transaction caches
            FundsCaches.TRANSACTIONS,
            // Calendar caches
            FundsCaches.CALENDAR, FundsCaches.CALENDAR_RANGE,
            // Balance caches
            FundsCaches.BALANCE, FundsCaches.MONTHLY_BALANCE,
            // Fee caches
            FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public void delete(@PathVariable("index") final long index) {
        service.delete(index);
    }

    /**
     * Returns all the transactions in a paginated form.
     *
     *
     * @param transaction
     *            query to filter transactions
     * @param page
     *            pagination to apply
     * @return a page for the transactions matching the sample
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = FundsCaches.TRANSACTIONS)
    public Iterable<Transaction> readAll(@Valid final TransactionQuery transaction, final Pageable page) {
        return service.getAll(transaction, page);
    }

    /**
     * Reads a single transaction by its id.
     *
     * @param index
     *            index of the transaction to read
     * @return the transaction for the id, or {@code null} if it doesn't exist
     */
    @GetMapping(path = "/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    @Cacheable(cacheNames = FundsCaches.TRANSACTION)
    public Transaction readOne(@PathVariable("index") final long index) {
        return service.getOne(index)
            .orElse(null);
    }

    /**
     * Updates a transaction.
     *
     * @param index
     *            index of the transaction to update
     * @param transaction
     *            updated transaction data
     * @return the updated transaction
     */
    @PutMapping(path = "/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = FundsCaches.TRANSACTION, key = "#result.index") },
            evict = { @CacheEvict(cacheNames = {
                    // Transaction caches
                    FundsCaches.TRANSACTIONS,
                    // Calendar caches
                    FundsCaches.CALENDAR, FundsCaches.CALENDAR_RANGE,
                    // Balance caches
                    FundsCaches.BALANCE, FundsCaches.MONTHLY_BALANCE,
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.FEE }, allEntries = true) })
    public Transaction update(@PathVariable("index") final long index,
            @Valid @RequestBody final TransactionChange transaction) {
        return service.update(index, transaction);
    }

}
