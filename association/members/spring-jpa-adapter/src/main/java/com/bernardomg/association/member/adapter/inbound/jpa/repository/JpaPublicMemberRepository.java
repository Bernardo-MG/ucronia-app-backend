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

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.PublicMemberSpecifications;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaPublicMemberRepository implements PublicMemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                log = LoggerFactory.getLogger(JpaPublicMemberRepository.class);

    private final PublicMemberSpringRepository publicMemberSpringRepository;

    public JpaPublicMemberRepository(final PublicMemberSpringRepository publicMemberSpringRepo) {
        super();

        publicMemberSpringRepository = Objects.requireNonNull(publicMemberSpringRepo);
    }

    @Override
    public final Page<PublicMember> findAll(final MemberFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final org.springframework.data.domain.Page<PublicMember> read;
        final Pageable                                           pageable;
        final Optional<Specification<PublicMemberEntity>>        spec;
        final Sorting                                            fixedSorting;

        log.debug("Finding all the members with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        fixedSorting = fixSorting(sorting);
        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        spec = PublicMemberSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = publicMemberSpringRepository.findAllByActiveTrue(pageable)
                .map(PublicMemberEntityMapper::toDomain);
        } else {
            read = publicMemberSpringRepository.findAll(spec.get(), pageable)
                .map(PublicMemberEntityMapper::toDomain);
        }

        log.debug("Found all the members with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            fixedSorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<PublicMember> findOne(final Long number) {
        final Optional<PublicMember> member;

        log.trace("Finding member with number {}", number);

        member = publicMemberSpringRepository.findByNumberAndActiveTrue(number)
            .map(PublicMemberEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    private final Sorting fixSorting(final Sorting sorting) {
        final Collection<Property> properties;

        properties = sorting.properties()
            .stream()
            // Fix name
            .map(prop -> {
                if (prop.name()
                    .startsWith("name.")) {
                    return new Property(prop.name()
                        .replaceFirst("name\\.", ""), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

}
