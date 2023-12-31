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

package com.bernardomg.association.membership.fee.controller;

import java.time.YearMonth;
import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.bernardomg.association.membership.cache.MembershipCaches;
import com.bernardomg.association.membership.fee.cache.FeeCaches;
import com.bernardomg.association.membership.fee.model.FeeQuery;
import com.bernardomg.association.membership.fee.model.FeeUpdate;
import com.bernardomg.association.membership.fee.model.FeesPaymentRequest;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/fee")
@AllArgsConstructor
@Transactional
public class FeeController {

    /**
     * Fee service.
     */
    private final FeeService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "FEE", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = {
            // Fee caches
            FeeCaches.FEES, FeeCaches.FEE, MembershipCaches.MONTHLY_BALANCE,
            // Funds caches
            FundsCaches.TRANSACTIONS, FundsCaches.TRANSACTION, FundsCaches.BALANCE, FundsCaches.MONTHLY_BALANCE,
            FundsCaches.CALENDAR, FundsCaches.CALENDAR_RANGE,
            // Member caches
            MembershipCaches.MEMBERS, MembershipCaches.MEMBER, MembershipCaches.CALENDAR,
            MembershipCaches.CALENDAR_RANGE }, allEntries = true) })
    public Collection<MemberFee> create(@Valid @RequestBody final FeesPaymentRequest fee) {
        return service.payFees(fee.getMemberNumber(), fee.getPaymentDate(), fee.getFeeDates());
    }

    @DeleteMapping(path = "/{date}/{memberNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = {
            // Fee caches
            FeeCaches.FEES,
            // Funds caches
            MembershipCaches.MONTHLY_BALANCE,
            // Member caches
            MembershipCaches.MEMBERS, MembershipCaches.MEMBER, MembershipCaches.CALENDAR,
            MembershipCaches.CALENDAR_RANGE }, allEntries = true),
            @CacheEvict(cacheNames = FeeCaches.FEE, key = "#p0") })
    public void delete(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM") final YearMonth date,
            @PathVariable("memberNumber") final long memberNumber) {
        service.delete(memberNumber, date);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEES)
    public Iterable<MemberFee> readAll(@Valid final FeeQuery query, final Pageable pageable) {
        return service.getAll(query, pageable);
    }

    @GetMapping(path = "/{date}/{memberNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEE, key = "#p0")
    public MemberFee readOne(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM") final YearMonth date,
            @PathVariable("memberNumber") final long memberNumber) {
        return service.getOne(memberNumber, date)
            .orElse(null);
    }

    @PutMapping(path = "/{date}/{memberNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.UPDATE)
    @Caching(
            put = { @CachePut(cacheNames = FeeCaches.FEE,
                    key = "#result.date.toString() + ':' + #result.memberNumber") },
            evict = { @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES,
                    // Member caches
                    MembershipCaches.MEMBERS, MembershipCaches.MEMBER, MembershipCaches.CALENDAR,
                    MembershipCaches.CALENDAR_RANGE, MembershipCaches.MONTHLY_BALANCE }, allEntries = true) })
    public MemberFee update(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM") final YearMonth date,
            @PathVariable("memberNumber") final long memberNumber, @Valid @RequestBody final FeeUpdate fee) {
        return service.update(memberNumber, date, fee);
    }

}
