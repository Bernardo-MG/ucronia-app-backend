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

package com.bernardomg.association.member.usecase.service;

import java.time.YearMonth;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.MemberBalanceQuery;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the membership balance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
@Slf4j
public final class DefaultMemberBalanceService implements MemberBalanceService {

    private final MemberBalanceRepository memberBalanceRepository;

    public DefaultMemberBalanceService(final MemberBalanceRepository memberBalanceRepo) {
        super();

        memberBalanceRepository = Objects.requireNonNull(memberBalanceRepo);
    }

    @Override
    public final Iterable<MonthlyMemberBalance> getMonthlyBalance(final MemberBalanceQuery balance, final Sort sort) {
        final YearMonth now;
        final YearMonth end;

        // Up to this month
        now = YearMonth.now();
        if ((balance.getEndDate() == null) || (balance.getEndDate()
            .isAfter(now))) {
            log.debug("Replacing end date {} with current date {}", balance.getEndDate(), now);
            end = now;
        } else {
            end = balance.getEndDate();
        }

        return memberBalanceRepository.findInRange(balance.getStartDate(), end, sort);
    }

}
