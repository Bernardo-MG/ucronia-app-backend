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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeChange;
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
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FeeApi;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeCreationDto;
import com.bernardomg.ucronia.openapi.model.FeeDto;
import com.bernardomg.ucronia.openapi.model.FeeMemberDto;
import com.bernardomg.ucronia.openapi.model.FeePaymentsDto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/fee")
public class FeeController implements FeeApi {

    /**
     * Fee service.
     */
    private final FeeService service;

    public FeeController(final FeeService service) {
        super();

        this.service = service;
    }

    private final Fee toDomain(final FeeChangeDto change, final YearMonth month, final long personNumber) {
        final Fee.Member                person;
        final Optional<Fee.Transaction> transaction;

        person = new Fee.Member(personNumber, null);
        if ((change.getPayment()
            .getIndex() == null)
                && ((change.getPayment()
                    .getDate() == null))) {
            transaction = Optional.empty();
        } else {
            transaction = Optional.of(new Fee.Transaction(change.getPayment()
                .getDate(),
                change.getPayment()
                    .getIndex()));
        }

        return new Fee(month, false, person, transaction);
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
    public FeeDto create(@Parameter(name = "FeeCreationDto", description = "",
            required = true) @Valid @RequestBody FeeCreationDto feeCreationDto) {
        final Fee fee;

        fee = service.createUnpaidFee(feeCreationDto.getMonth(), feeCreationDto.getMember());

        return toDto(fee);
    }

    private final FeeDto toDto(Fee fee) {
        final ContactNameDto contactName;
        final FeeMemberDto   feeMember;

        contactName = new ContactNameDto().firstName(fee.member()
            .name()
            .firstName())
            .lastName(fee.member()
                .name()
                .lastName())
            .fullName(fee.member()
                .name()
                .fullName());
        feeMember = new FeeMemberDto().name(contactName)
            .number(fee.member()
                .number());
        return new FeeDto().month(fee.month())
            .paid(fee.paid())
            .member(feeMember);
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
    public void
            delete(@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$") @Parameter(name = "month", description = "",
                    required = true, in = ParameterIn.PATH) @PathVariable("month") YearMonth month,
                    @Parameter(name = "personNumber", description = "", required = true,
                            in = ParameterIn.PATH) @PathVariable("personNumber") Long personNumber) {
        service.delete(personNumber, month);
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEES)
    public List<FeeDto> getAll(
            @Parameter(name = "date", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date",
                    required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable Instant date,
            @Parameter(name = "startDate", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(
                    value = "startDate",
                    required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable Instant startDate,
            @Parameter(name = "endDate", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(
                    value = "endDate",
                    required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable Instant endDate,
            @Parameter(name = "page", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page",
                    required = false) @Nullable Integer page,
            @Parameter(name = "size", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size",
                    required = false) @Nullable Integer size,
            @Parameter(name = "sort", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort",
                    required = false) @Nullable List<String> sort) {
        final FeeQuery   query;
        final Pagination pagination;
        final Sorting    sorting;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        query = new FeeQuery(date, startDate, endDate);
        return service.getAll(query, pagination, sorting)
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.FEE, key = "#p0.toString() + ':' + #p1")
    public FeeDto getOne(
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$") @Parameter(name = "month", description = "", required = true, in = ParameterIn.PATH) @PathVariable("month") YearMonth month,
            @Parameter(name = "personNumber", description = "", required = true, in = ParameterIn.PATH) @PathVariable("personNumber") Integer personNumber
        ) {
        return service.getOne(personNumber, month)
                .map(this::toDto)
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
    public List<FeeDto> pay(
            @Parameter(name = "FeePaymentsDto", description = "", required = true) @Valid @RequestBody FeePaymentsDto feePaymentsDto
            ) {
        return service.payFees(feePaymentsDto.getFeeMonths(), feePaymentsDto.getMember(),
            feePaymentsDto.getPayment()).stream().map(this::toDto).toList();
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
    public FeeDto update(
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$") @Parameter(name = "month", description = "", required = true, in = ParameterIn.PATH) @PathVariable("month") YearMonth month,
            @Parameter(name = "personNumber", description = "", required = true, in = ParameterIn.PATH) @PathVariable("personNumber") Integer personNumber,
            @Parameter(name = "FeeChangeDto", description = "", required = true) @Valid @RequestBody FeeChangeDto feeChangeDto
        ) {
        final Fee fee;
        final Fee updated;

        fee = toDomain(feeChangeDto, month, personNumber);
        updated = service.update(fee);
        return toDto(updated);
    }

}
