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

import java.time.Year;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.cache.FeeCaches;
import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.usecase.service.FeeCalendarService;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FeeCalendarApi;
import com.bernardomg.ucronia.openapi.model.ContactDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarYearsRangeDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;

import jakarta.validation.Valid;

/**
 * Member fee calendar REST controller.
 *
 * TODO: rework this model
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/fee/calendar")
public class FeeCalendarController implements FeeCalendarApi {

    /**
     * Member fee calendar service.
     */
    private final FeeCalendarService service;

    public FeeCalendarController(final FeeCalendarService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.CALENDAR)
    public List<FeeCalendarDto> getForYear(final Integer year, @Valid final String status,
            @Valid final List<String> sort) {
        final MemberStatus memberStatus;
        final Sorting      sorting;

        memberStatus = MemberStatus.valueOf(status);
        sorting = WebSorting.toSorting(sort);
        return service.getYear(Year.of(year), memberStatus, sorting)
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = FeeCaches.CALENDAR_RANGE)
    public FeeCalendarYearsRangeDto getYearsRange() {
        final FeeCalendarYearsRange range;
        final List<Integer>         years;

        range = service.getRange();

        years = range.years()
            .stream()
            .map(Year::getValue)
            .toList();
        return new FeeCalendarYearsRangeDto().years(years);
    }

    private final FeeCalendarDto toDto(final FeeCalendar feeCalendar) {
        final ContactDto     contact;
        final ContactNameDto contactName;
        final MembershipDto  membership;

        contactName = new ContactNameDto().firstName(feeCalendar.member()
            .name()
            .firstName())
            .lastName(feeCalendar.member()
                .name()
                .lastName())
            .fullName(feeCalendar.member()
                .name()
                .fullName());
        membership = new MembershipDto().active(feeCalendar.member()
            .membership()
            .active());
        contact = new ContactDto().name(contactName)
            .membership(membership)
            .number(feeCalendar.member()
                .number());
        return new FeeCalendarDto().year(feeCalendar.year())
            .member(contact);
    }

}
