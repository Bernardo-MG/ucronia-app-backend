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

package com.bernardomg.association.controller.fee;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.membership.cache.MembershipCaches;
import com.bernardomg.association.model.fee.FeeCalendarQuery;
import com.bernardomg.association.model.fee.MemberFeeCalendar;
import com.bernardomg.association.model.fee.YearsRange;
import com.bernardomg.association.service.member.MemberFeeCalendarService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import lombok.AllArgsConstructor;

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
@AllArgsConstructor
@Transactional
public class MemberFeeCalendarController {

    /**
     * Member fee calendar service.
     */
    private final MemberFeeCalendarService service;

    /**
     * Returns the range of available years.
     *
     * @return the range of available years
     */
    @GetMapping(path = "/range", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = MembershipCaches.CALENDAR_RANGE)
    public YearsRange readRange() {
        return service.getRange();
    }

    /**
     * Returns all the member fees for a year.
     *
     * @param year
     *            year to read
     * @param request
     *            request data
     * @param sort
     *            sorting to apply
     * @return all the member fees for a year
     */
    @GetMapping(path = "/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "FEE", action = Actions.READ)
    @Cacheable(cacheNames = MembershipCaches.CALENDAR)
    public Iterable<MemberFeeCalendar> readYear(@PathVariable("year") final Integer year,
            final FeeCalendarQuery request, final Sort sort) {
        return service.getYear(year, request.getStatus(), sort);
    }

}
