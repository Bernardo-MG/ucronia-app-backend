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

package com.bernardomg.association.fee.domain.repository;

import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface FeeRepository {

    public void delete(final Long number, final YearMonth date);

    public boolean exists(final Long number, final YearMonth date);

    public boolean existsPaid(final Long number, final YearMonth date);

    public Page<Fee> findAll(final FeeQuery query, final Pagination pagination, final Sorting sorting);

    public Page<Fee> findAllForPerson(final Long number, final Pagination pagination, final Sorting sorting);

    public Collection<Fee> findAllInMonth(final YearMonth date);

    public Collection<Fee> findAllInYear(final Year year, final Sorting sorting);

    public Collection<Fee> findAllInYearForActiveMembers(final Year year, final Sorting sorting);

    public Collection<Fee> findAllInYearForInactiveMembers(final Year year, final Sorting sorting);

    public Optional<Fee> findOne(final Long number, final YearMonth date);

    public YearsRange findRange();

    public Collection<Fee> save(final Collection<Fee> fees);

    public Fee save(final Fee fee);

}
