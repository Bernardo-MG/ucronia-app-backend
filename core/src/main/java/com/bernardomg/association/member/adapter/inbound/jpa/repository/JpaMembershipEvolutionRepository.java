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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MembershipEvolutionMonthEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MembershipEvolutionMonthEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MembershipEvolutionSpecifications;
import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaMembershipEvolutionRepository implements MembershipEvolutionRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory
        .getLogger(JpaMembershipEvolutionRepository.class);

    private final MembershipEvolutionSpringRepository membershipEvolutionSpringRepository;

    public JpaMembershipEvolutionRepository(final MembershipEvolutionSpringRepository membershipEvolutionSpringRepo) {
        super();

        membershipEvolutionSpringRepository = Objects.requireNonNull(membershipEvolutionSpringRepo);
    }

    @Override
    public final Collection<MembershipEvolutionMonth> findInRange(final Instant from, final Instant to,
            final Sorting sorting) {
        final Optional<Specification<MembershipEvolutionMonthEntity>> spec;
        final Collection<MembershipEvolutionMonthEntity>              evolutionEntities;
        final Collection<MembershipEvolutionMonth>                    evolution;
        final Sort                                                    sort;

        // TODO: the dates are optional

        log.debug("Finding membership evolution from {} to {} sorting by {}", from, to, sorting);

        spec = MembershipEvolutionSpecifications.inRange(from, to);

        sort = SpringSorting.toSort(sorting);
        if (spec.isPresent()) {
            evolutionEntities = membershipEvolutionSpringRepository.findAll(spec.get(), sort);
        } else {
            evolutionEntities = membershipEvolutionSpringRepository.findAll(sort);
        }

        evolution = evolutionEntities.stream()
            .map(MembershipEvolutionMonthEntityMapper::toDomain)
            .toList();

        log.debug("Found membership evolution from {} to {}: {}", from, to, evolution);

        return evolution;
    }

}
