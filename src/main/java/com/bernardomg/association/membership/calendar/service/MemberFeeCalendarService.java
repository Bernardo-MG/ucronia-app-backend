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

package com.bernardomg.association.membership.calendar.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.YearsRange;
import com.bernardomg.association.membership.member.model.MemberStatus;

/**
 * Fee calendar service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MemberFeeCalendarService {

    /**
     * Returns the range of available years. These are all the years which have fees assigned, except for any future
     * year.
     *
     * @return the range of available years
     */
    public YearsRange getRange();

    /**
     * Returns all the member fees for a year.
     *
     * @param year
     *            year to read
     * @param status
     *            member active status
     * @param sort
     *            sorting to apply
     * @return all the member fees for a year
     */
    public Iterable<MemberFeeCalendar> getYear(final int year, final MemberStatus status, final Sort sort);

}
