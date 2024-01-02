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

package com.bernardomg.association.membership.fee.persistence.repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;

public interface FeeRepository extends JpaRepository<FeeEntity, Long> {

    public boolean existsByMemberIdAndDate(final Long memberId, final YearMonth date);

    /**
     * Returns all the fees in the received date.
     *
     * @param date
     *            date to filter by
     * @return all the fees in the date
     */
    public List<FeeEntity> findAllByDate(final YearMonth date);

    /**
     * Finds the fee for the member in the date.
     *
     * @param memberId
     *            member to filter by
     * @param date
     *            date to filter by
     * @return fee for the member in the date
     */
    public Optional<FeeEntity> findOneByMemberIdAndDate(final Long memberId, final YearMonth date);

}
