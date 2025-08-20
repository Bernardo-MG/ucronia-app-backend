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

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeDtoMapper;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.person.adapter.outbound.cache.PersonsCaches;
import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FeeApi;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeCreationDto;
import com.bernardomg.ucronia.openapi.model.FeeDto;
import com.bernardomg.ucronia.openapi.model.FeePageDto;
import com.bernardomg.ucronia.openapi.model.FeePaymentsDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class FeeController implements FeeApi {

    /**
     * Fee service.
     */
    private final FeeService service;

    public FeeController(final FeeService service) {
        super();

        this.service = service;
    }

    @Override
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
    public FeeDto createUnpaidFee(@Valid final FeeCreationDto feeCreationDto) {
        final Fee       fee;
        final YearMonth month;

        month = YearMonth.from(feeCreationDto.getMonth()
            .atZone(ZoneOffset.UTC));
        fee = service.createUnpaidFee(month, feeCreationDto.getMember());

        return FeeDtoMapper.toDto(fee);
    }

    @Override
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
    public FeeDto deleteFee(final Long personNumber, final YearMonth month) {
        final Fee fee;

        fee = service.delete(personNumber, month);

        return FeeDtoMapper.toDto(fee);
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEES)
    public FeePageDto getAllFees(@Valid final Instant date, @Valid final Instant startDate,
            @Valid final Instant endDate, @Min(0) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort) {
        final FeeQuery   query;
        final Pagination pagination;
        final Sorting    sorting;
        final Page<Fee>  fees;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        query = new FeeQuery(date, startDate, endDate);
        fees = service.getAll(query, pagination, sorting);

        return FeeDtoMapper.toDto(fees);
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEE, key = "#p0.toString() + ':' + #p1")
    public FeeDto getOneFee(final Long personNumber, final YearMonth month) {
        return service.getOne(personNumber, month)
            .map(FeeDtoMapper::toDto)
            .orElse(null);
    }

    @Override
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
    public List<FeeDto> payFee(@Valid final FeePaymentsDto feePaymentsDto) {
        return service.payFees(feePaymentsDto.getMonths(), feePaymentsDto.getMember(), feePaymentsDto.getTransaction())
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList();
    }

    @Override
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
    public FeeDto updateFee(final Long personNumber, final YearMonth month, @Valid final FeeChangeDto feeChangeDto) {
        final Fee fee;
        final Fee updated;

        fee = FeeDtoMapper.toDomain(feeChangeDto, month, personNumber);
        updated = service.update(fee);
        return FeeDtoMapper.toDto(updated);
    }

}
