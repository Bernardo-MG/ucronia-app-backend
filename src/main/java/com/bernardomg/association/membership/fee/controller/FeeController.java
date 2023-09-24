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

package com.bernardomg.association.membership.fee.controller;

import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
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

import com.bernardomg.association.funds.cache.FundsCaches;
import com.bernardomg.association.membership.cache.MembershipCaches;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeesPaymentRequest;
import com.bernardomg.association.membership.fee.model.request.ValidatedFeeQuery;
import com.bernardomg.association.membership.fee.model.request.ValidatedFeeUpdate;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.security.permission.authorization.AuthorizedResource;
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
@RequestMapping("/fee")
@AllArgsConstructor
public class FeeController {

    /**
     * Fee service.
     */
    private final FeeService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @AuthorizedResource(resource = "FEE", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = {
            // Fee caches
            MembershipCaches.FEES, MembershipCaches.FEE, MembershipCaches.MONTHLY_BALANCE, MembershipCaches.CALENDAR,
            MembershipCaches.CALENDAR_RANGE,
            // Funds caches
            FundsCaches.TRANSACTIONS, FundsCaches.TRANSACTION, FundsCaches.BALANCE, FundsCaches.MONTHLY_BALANCE,
            FundsCaches.CALENDAR, FundsCaches.CALENDAR_RANGE }, allEntries = true) })
    public Collection<? extends MemberFee> create(@Valid @RequestBody final FeesPaymentRequest fee) {
        return service.payFees(fee);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "FEE", action = Actions.DELETE)
    @Caching(evict = {
            @CacheEvict(cacheNames = { MembershipCaches.FEES, MembershipCaches.CALENDAR,
                    MembershipCaches.CALENDAR_RANGE, MembershipCaches.MONTHLY_BALANCE }, allEntries = true),
            @CacheEvict(cacheNames = MembershipCaches.FEE, key = "#id") })
    public void delete(@PathVariable("id") final long id) {
        service.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = MembershipCaches.FEES)
    public Iterable<MemberFee> readAll(@Valid final ValidatedFeeQuery query, final Pageable pageable) {
        return service.getAll(query, pageable);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = MembershipCaches.FEE, key = "#id")
    public MemberFee readOne(@PathVariable("id") final long id) {
        return service.getOne(id)
            .orElse(null);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "FEE", action = Actions.UPDATE)
    @Caching(put = { @CachePut(cacheNames = MembershipCaches.FEE, key = "#result.id") },
            evict = {
                    @CacheEvict(
                            cacheNames = { MembershipCaches.FEES, MembershipCaches.CALENDAR,
                                    MembershipCaches.CALENDAR_RANGE, MembershipCaches.MONTHLY_BALANCE },
                            allEntries = true) })
    public MemberFee update(@PathVariable("id") final long id, @Valid @RequestBody final ValidatedFeeUpdate fee) {
        return service.update(id, fee);
    }

}
