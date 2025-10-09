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

package com.bernardomg.association.fee.usecase.service;

import java.time.Year;
import java.util.Collection;

import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.data.domain.Sorting;

/**
 * Fee calendar service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeCalendarService {

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
     * @param sorting
     *            sorting to apply
     * @return all the member fees for a year
     */
    public Collection<MemberFees> getYear(final Year year, final MemberStatus status, final Sorting sorting);

}
