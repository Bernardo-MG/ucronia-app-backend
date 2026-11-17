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

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeSorting;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MyFeeRow;
import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.association.fee.domain.repository.MyFeeRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional(readOnly = true)
public final class JpaMyFeeRepository implements MyFeeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(JpaMyFeeRepository.class);

    private final FeeSpringRepository feeSpringRepository;

    public JpaMyFeeRepository(final FeeSpringRepository feeSpringRepo) {
        super();

        feeSpringRepository = Objects.requireNonNull(feeSpringRepo);
    }

    @Override
    public final Page<MyFee> findAllForMember(final Long number, final Pagination pagination, final Sorting sorting) {
        final Pageable                                    pageable;
        final Sorting                                     fixedSorting;
        final org.springframework.data.domain.Page<MyFee> found;

        fixedSorting = FeeSorting.fixSorting(sorting);

        log.debug("Finding all fees for profile {} with pagination {} and sorting {}", number, pagination,
            fixedSorting);

        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        found = feeSpringRepository.findMyFeesByMemberNumber(number, pageable)
            .map(MyFeeRow::toDomain);

        log.debug("Found all fees for profile {} with pagination {} and sorting {}: {}", number, pagination, sorting,
            found);

        return SpringPagination.toPage(found);
    }

}
