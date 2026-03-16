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

package com.bernardomg.association.member.usecase.service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionQuery;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the membership evolution service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMembershipEvolutionService implements MembershipEvolutionService {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(DefaultMembershipEvolutionService.class);

    private final MembershipEvolutionRepository membershipEvolutionRepository;

    public DefaultMembershipEvolutionService(final MembershipEvolutionRepository membershipEvolutionRepo) {
        super();

        membershipEvolutionRepository = Objects.requireNonNull(membershipEvolutionRepo);
    }

    @Override
    public final Collection<MembershipEvolutionMonth> getMonthlyEvolution(final MembershipEvolutionQuery query) {
        final Instant                              now;
        final Instant                              end;
        final Sorting                              sorting;
        final Collection<MembershipEvolutionMonth> evolution;

        log.debug("Reading monthly membership evolution with query {}", query);

        // Up to this month
        now = YearMonth.now()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        if ((query.to() == null) || (query.to()
            .isAfter(now))) {
            log.debug("Replacing end date {} with current date {}", query.to(), now);
            end = now;
        } else {
            end = query.to();
        }

        sorting = new Sorting(List.of(Sorting.Property.asc("month")));
        evolution = membershipEvolutionRepository.findInRange(query.from(), end, sorting);

        log.debug("Read monthly membership evolution with query {}: {}", query, evolution);

        return evolution;
    }

}
