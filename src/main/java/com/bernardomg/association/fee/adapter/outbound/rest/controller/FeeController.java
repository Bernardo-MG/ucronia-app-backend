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

package com.bernardomg.association.fee.adapter.outbound.rest.controller;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeChange;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeCreation;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeePayments;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.person.adapter.outbound.cache.PersonsCaches;
import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import jakarta.validation.Valid;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/fee")
public class FeeController {

    /**
     * Fee service.
     */
    private final FeeService service;

    public FeeController(final FeeService service) {
        super();

        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "FEE", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = FeeCaches.FEE, key = "#result.month + ':' + #result.person.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.CALENDAR, FeeCaches.CALENDAR_RANGE,
                    // Funds caches
                    TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
                    TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Member caches
                    MembersCaches.MONTHLY_BALANCE, MembersCaches.MEMBERS, MembersCaches.MEMBER,
                    // Person caches
                    PersonsCaches.PERSON, PersonsCaches.PERSONS }, allEntries = true) })
    public Fee create(@Valid @RequestBody final FeeCreation fee) {
        return service.createUnpaidFee(fee.month(), fee.person()
            .number());
    }

    @DeleteMapping(path = "/{month}/{personNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { FeeCaches.FEE }, key = "#p0.toString() + ':' + #p1"),
            @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.CALENDAR, FeeCaches.CALENDAR_RANGE,
                    // Funds caches
                    MembersCaches.MONTHLY_BALANCE,
                    // Member caches
                    MembersCaches.MEMBERS, MembersCaches.MEMBER,
                    // Person caches
                    PersonsCaches.PERSON, PersonsCaches.PERSONS }, allEntries = true) })
    public void delete(@PathVariable("month") final YearMonth month,
            @PathVariable("personNumber") final long personNumber) {
        service.delete(personNumber, month);
    }

    @PostMapping(path = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "FEE", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = {
            // Fee caches
            FeeCaches.FEES, FeeCaches.CALENDAR, FeeCaches.CALENDAR_RANGE,
            // Funds caches
            TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
            TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
            // Member caches
            MembersCaches.MONTHLY_BALANCE, MembersCaches.MEMBERS, MembersCaches.MEMBER,
            // Person caches
            PersonsCaches.PERSON, PersonsCaches.PERSONS }, allEntries = true) })
    public Collection<Fee> pay(@Valid @RequestBody final FeePayments payment) {
        return service.payFees(payment.feeMonths(), payment.person()
            .number(),
            payment.payment()
                .date());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEES)
    public Page<Fee> readAll(@Valid final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        return service.getAll(query, pagination, sorting);
    }

    @GetMapping(path = "/{month}/{personNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEE, key = "#p0.toString() + ':' + #p1")
    public Fee readOne(@PathVariable("month") final YearMonth month,
            @PathVariable("personNumber") final long personNumber) {
        return service.getOne(personNumber, month)
            .orElse(null);
    }

    @PutMapping(path = "/{month}/{personNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.CREATE)
    @Caching(put = { @CachePut(cacheNames = FeeCaches.FEE, key = "#result.month + ':' + #result.person.number") },
            evict = { @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.CALENDAR, FeeCaches.CALENDAR_RANGE,
                    // Funds caches
                    TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
                    TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Member caches
                    MembersCaches.MONTHLY_BALANCE, MembersCaches.MEMBERS, MembersCaches.MEMBER,
                    // Person caches
                    PersonsCaches.PERSON, PersonsCaches.PERSONS }, allEntries = true) })
    public Fee update(@PathVariable("month") final YearMonth month,
            @PathVariable("personNumber") final long personNumber, @Valid @RequestBody final FeeChange change) {
        final Fee fee;

        fee = toDomain(change, month, personNumber);
        return service.update(fee);
    }

    private final Fee toDomain(final FeeChange change, final YearMonth month, final long personNumber) {
        final Fee.Person                person;
        final Optional<Fee.Transaction> transaction;

        person = new Fee.Person(personNumber, null);
        if ((change.payment()
            .index() == null)
                && ((change.payment()
                    .date() == null))) {
            transaction = Optional.empty();
        } else {
            transaction = Optional.of(new Fee.Transaction(change.payment()
                .date(),
                change.payment()
                    .index()));
        }

        return new Fee(month, false, person, transaction);
    }

}
