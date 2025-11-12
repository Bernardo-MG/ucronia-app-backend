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

package com.bernardomg.association.fee.adapter.outbound.rest.controller;

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.contact.adapter.outbound.cache.ContactsCaches;
import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeDtoMapper;
import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.member.adapter.outbound.cache.PublicMembersCaches;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.transaction.adapter.outbound.cache.TransactionCaches;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FeeApi;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeCreationDto;
import com.bernardomg.ucronia.openapi.model.FeePageResponseDto;
import com.bernardomg.ucronia.openapi.model.FeePaymentsDto;
import com.bernardomg.ucronia.openapi.model.FeeResponseDto;
import com.bernardomg.ucronia.openapi.model.FeesResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberFeesResponseDto;
import com.bernardomg.ucronia.openapi.model.YearsRangeResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
    @RequireResourceAuthorization(resource = "FEE", action = Actions.CREATE)
    @Caching(
            put = { @CachePut(cacheNames = FeeCaches.FEE,
                    key = "#result.content.member.number + ':' + #result.content.month") },
            evict = { @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.MEMBER_FEES, FeeCaches.YEAR_RANGE,
                    // Funds caches
                    TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
                    TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Member caches
                    MembersCaches.MONTHLY_BALANCE, PublicMembersCaches.PUBLIC_MEMBERS,
                    PublicMembersCaches.PUBLIC_MEMBER,
                    // Contact caches
                    ContactsCaches.CONTACT, ContactsCaches.CONTACTS }, allEntries = true) })
    public FeeResponseDto createUnpaidFee(@Valid final FeeCreationDto feeCreationDto) {
        final Fee fee;

        fee = service.createUnpaidFee(feeCreationDto.getMonth(), feeCreationDto.getMember());

        return FeeDtoMapper.toResponseDto(fee);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.DELETE)
    @Caching(evict = { @CacheEvict(cacheNames = { FeeCaches.FEE }, key = "#p0 + ':' + #p1"), @CacheEvict(cacheNames = {
            // Fee caches
            FeeCaches.FEES, FeeCaches.MEMBER_FEES, FeeCaches.YEAR_RANGE,
            // Funds caches
            MembersCaches.MONTHLY_BALANCE,
            // Member caches
            PublicMembersCaches.PUBLIC_MEMBERS, PublicMembersCaches.PUBLIC_MEMBER,
            // Contact caches
            ContactsCaches.CONTACT, ContactsCaches.CONTACTS }, allEntries = true) })
    public FeeResponseDto deleteFee(final Long member, final YearMonth month) {
        final Fee fee;

        fee = service.delete(member, month);

        return FeeDtoMapper.toResponseDto(fee);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEES)
    public FeePageResponseDto getAllFees(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort, @Valid final Instant date, @Valid final Instant from,
            @Valid final Instant to) {
        final FeeQuery   query;
        final Pagination pagination;
        final Sorting    sorting;
        final Page<Fee>  fees;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        query = new FeeQuery(date, from, to);
        fees = service.getAll(query, pagination, sorting);

        return FeeDtoMapper.toResponseDto(fees);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.YEAR_RANGE)
    public YearsRangeResponseDto getFeesYearsRange() {
        final YearsRange range;

        range = service.getRange();

        return FeeDtoMapper.toResponseDto(range);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.MEMBER_FEES)
    public MemberFeesResponseDto getMemberFees(final Integer year, @NotNull @Valid final String status,
            @Valid final List<String> sort) {
        final MemberStatus           memberStatus;
        final Sorting                sorting;
        final Collection<MemberFees> fees;

        // TODO: use the fees listing and filter
        memberStatus = MemberStatus.valueOf(status);
        sorting = WebSorting.toSorting(sort);
        fees = service.getForYear(Year.of(year), memberStatus, sorting);
        return FeeDtoMapper.toMemberResponseDto(fees);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEE, key = "#p0 + ':' + #p1")
    public FeeResponseDto getOneFee(final Long member, final YearMonth month) {
        final Optional<Fee> fee;

        fee = service.getOne(member, month);

        return FeeDtoMapper.toResponseDto(fee);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.CREATE)
    @Caching(evict = { @CacheEvict(cacheNames = {
            // Fee caches
            FeeCaches.FEES, FeeCaches.MEMBER_FEES, FeeCaches.YEAR_RANGE,
            // Funds caches
            TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
            TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
            // Member caches
            MembersCaches.MONTHLY_BALANCE, PublicMembersCaches.PUBLIC_MEMBERS, PublicMembersCaches.PUBLIC_MEMBER,
            // Contact caches
            ContactsCaches.CONTACT, ContactsCaches.CONTACTS }, allEntries = true) })
    public FeesResponseDto payFee(@Valid final FeePaymentsDto feePaymentsDto) {
        final Collection<Fee> fees;
        final FeePayments     payments;

        payments = FeeDtoMapper.toDomain(feePaymentsDto);
        fees = service.payFees(payments);

        return FeeDtoMapper.toResponseDto(fees);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.CREATE)
    @Caching(
            put = { @CachePut(cacheNames = FeeCaches.FEE,
                    key = "#result.content.member.number + ':' + #result.content.month") },
            evict = { @CacheEvict(cacheNames = {
                    // Fee caches
                    FeeCaches.FEES, FeeCaches.MEMBER_FEES, FeeCaches.YEAR_RANGE,
                    // Funds caches
                    TransactionCaches.TRANSACTIONS, TransactionCaches.TRANSACTION, TransactionCaches.BALANCE,
                    TransactionCaches.MONTHLY_BALANCE, TransactionCaches.CALENDAR, TransactionCaches.CALENDAR_RANGE,
                    // Member caches
                    MembersCaches.MONTHLY_BALANCE, PublicMembersCaches.PUBLIC_MEMBERS,
                    PublicMembersCaches.PUBLIC_MEMBER,
                    // Contact caches
                    ContactsCaches.CONTACT, ContactsCaches.CONTACTS }, allEntries = true) })
    public FeeResponseDto updateFee(final Long member, final YearMonth month, @Valid final FeeChangeDto feeChangeDto) {
        final Fee fee;
        final Fee updated;

        fee = FeeDtoMapper.toDomain(feeChangeDto, month, member);
        updated = service.update(fee);
        return FeeDtoMapper.toResponseDto(updated);
    }

}
