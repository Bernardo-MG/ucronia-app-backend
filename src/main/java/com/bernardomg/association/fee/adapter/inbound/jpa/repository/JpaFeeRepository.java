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

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntityMapper;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.specification.FeeSpecifications;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaFeeRepository implements FeeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log                = LoggerFactory.getLogger(JpaFeeRepository.class);

    private static final Collection<String>     PROFILE_PROPERTIES = List.of("firstName", "lastName", "number");

    private final FeeSpringRepository           feeSpringRepository;

    private final FeeTypeSpringRepository       feeTypeSpringRepository;

    private final MemberProfileSpringRepository memberProfileSpringRepository;

    private final TransactionSpringRepository   transactionSpringRepository;

    public JpaFeeRepository(final FeeSpringRepository feeSpringRepo,
            final MemberProfileSpringRepository memberProfileSpringRepo,
            final FeeTypeSpringRepository feeTypeSpringRepo, final TransactionSpringRepository transactionSpringRepo) {
        super();

        feeSpringRepository = Objects.requireNonNull(feeSpringRepo);
        memberProfileSpringRepository = Objects.requireNonNull(memberProfileSpringRepo);
        feeTypeSpringRepository = Objects.requireNonNull(feeTypeSpringRepo);
        transactionSpringRepository = Objects.requireNonNull(transactionSpringRepo);
    }

    @Override
    public final void delete(final Long number, final YearMonth date) {
        final Optional<MemberProfileEntity> member;
        final Instant                       dateParsed;

        log.debug("Deleting fee for member {} in date {}", number, date);

        // TODO: only members
        member = memberProfileSpringRepository.findByNumber(number);
        if (member.isPresent()) {
            dateParsed = date.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
            feeSpringRepository.deleteByMemberIdAndMonth(member.get()
                .getId(), dateParsed);

            log.debug("Deleted fee for member {} in date {}", number, date);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete fee for member {} in date {}, as the member doesn't exist", number, date);
        }
    }

    @Override
    public final boolean exists(final Long number, final YearMonth date) {
        final boolean exists;
        final Instant dateParsed;

        log.debug("checking a fee exists for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        exists = feeSpringRepository.existsByMemberNumberAndMonth(number, dateParsed);

        log.debug("Fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final boolean existsPaid(final Long number, final YearMonth date) {
        final boolean exists;
        final Instant dateParsed;

        log.debug("checking a paid fee exists for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        exists = feeSpringRepository.existsByMemberNumberAndMonthAndPaid(number, dateParsed);

        log.debug("Paid fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final Page<Fee> findAll(final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        final Optional<Specification<FeeEntity>>        spec;
        final org.springframework.data.domain.Page<Fee> found;
        final Pageable                                  pageable;
        final Sorting                                   fixedSorting;
        // TODO: Test reading with no last name

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all fees with query {}, pagination {} and sorting {}", query, pagination, fixedSorting);

        spec = FeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            pageable = SpringPagination.toPageable(pagination, fixedSorting);
            found = feeSpringRepository.findAll(pageable)
                .map(FeeEntityMapper::toDomain);
        } else {
            pageable = SpringPagination.toPageable(pagination, sorting);
            found = feeSpringRepository.findAll(spec.get(), pageable)
                .map(FeeEntityMapper::toDomain);
        }

        log.debug("Found all fees with query {}, pagination {} and sorting {}: {}", query, pagination, sorting, found);

        return SpringPagination.toPage(found);
    }

    @Override
    public final Page<Fee> findAllForProfile(final Long number, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Fee> found;
        final Pageable                                  pageable;
        final Sorting                                   fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all fees for profile {} with pagination {} and sorting {}", number, pagination,
            fixedSorting);

        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        found = feeSpringRepository.findAllByMemberNumber(number, pageable)
            .map(FeeEntityMapper::toDomain);

        log.debug("Found all fees for profile {} with pagination {} and sorting {}: {}", number, pagination, sorting,
            found);

        return SpringPagination.toPage(found);
    }

    @Override
    public final Collection<Fee> findAllInYear(final Year year, final Sorting sorting) {
        final Collection<Fee> fees;
        final Sort            sort;
        final Sorting         fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all fees in year {} sorting {}", year, fixedSorting);

        sort = SpringSorting.toSort(fixedSorting);
        fees = feeSpringRepository.findAllForYear(year.getValue(), sort)
            .stream()
            .map(FeeEntityMapper::toDomain)
            .toList();

        log.debug("Found all fees in year {} sorting {}: {}", year, fixedSorting, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInYearForActiveMembers(final Year year, final Sorting sorting) {
        final Collection<Long> foundIds;
        final Collection<Fee>  found;
        final Sort             sort;
        final Sorting          fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all fees for active members in year {} sorting {}", year, fixedSorting);

        foundIds = memberProfileSpringRepository.findAllActiveMemberIds();

        log.debug("Active members: {}", foundIds);

        sort = SpringSorting.toSort(fixedSorting);
        found = feeSpringRepository.findAllForYearAndMembersIn(year.getValue(), foundIds, sort)
            .stream()
            .map(FeeEntityMapper::toDomain)
            .toList();

        log.debug("Found all fees for active members in year {} sorting {}: {}", year, fixedSorting, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllInYearForInactiveMembers(final Year year, final Sorting sorting) {
        final Collection<Long> foundIds;
        final Collection<Fee>  found;
        final Sort             sort;
        final Sorting          fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all fees for inactive members in year {} sorting {}", year, fixedSorting);

        foundIds = memberProfileSpringRepository.findAllInactiveMemberIds();

        log.debug("Inactive members: {}", foundIds);

        sort = SpringSorting.toSort(fixedSorting);
        found = feeSpringRepository.findAllForYearAndMembersIn(year.getValue(), foundIds, sort)
            .stream()
            .map(FeeEntityMapper::toDomain)
            .toList();

        log.debug("Found all fees for inactive members in year {} sorting {}: {}", year, fixedSorting, found);

        return found;
    }

    @Override
    public final Optional<Fee> findOne(final Long number, final YearMonth date) {
        final Optional<Fee> found;
        final Instant       dateParsed;

        log.debug("Finding fee for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        found = feeSpringRepository.findByMemberNumberAndDate(number, dateParsed)
            .map(FeeEntityMapper::toDomain);

        log.debug("Found fee for member {} in date {}: {}", number, date, found);

        return found;
    }

    @Override
    public final YearsRange findRange() {
        final Collection<Year> years;
        final YearsRange       range;

        log.debug("Finding fees range");

        years = feeSpringRepository.findYears()
            .stream()
            .map(Year::of)
            .toList();
        range = new YearsRange(years);

        log.debug("Found fees range: {}", range);

        return range;
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;
        final Collection<Fee>       saved;

        log.debug("Saving fees {}", fees);

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        // TODO: use map
        entities.forEach(this::loadId);
        saved = feeSpringRepository.saveAll(entities)
            .stream()
            .map(FeeEntityMapper::toDomain)
            .toList();

        log.debug("Saved fees {}", fees);

        return saved;
    }

    @Override
    public final Fee save(final Fee fee) {
        final FeeEntity entity;
        final FeeEntity saved;

        log.debug("Saving fee {}", fee);

        entity = toEntity(fee);
        loadId(entity);
        saved = feeSpringRepository.save(entity);

        log.debug("Saved fee {}", fee);

        return FeeEntityMapper.toDomain(saved);
    }

    private final Sorting fixSorting(final Sorting sorting) {
        final Collection<Property> properties;

        properties = sorting.properties()
            .stream()
            .map(prop -> {
                if (PROFILE_PROPERTIES.contains(prop.name())) {
                    return new Property("member.profile." + prop.name(), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        // TODO: optimize to use a single query
        read = feeSpringRepository.findByMemberIdAndMonth(fee.getMember()
            .getId(), fee.getMonth());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final FeeEntity toEntity(final Fee fee) {
        final Optional<MemberProfileEntity> member;
        final Optional<FeeTypeEntity>       feeType;
        final Optional<TransactionEntity>   transaction;
        final boolean                       paid;
        final FeeEntity                     entity;
        final Instant                       date;

        // TODO: move to mapper

        member = memberProfileSpringRepository.findByNumber(fee.member()
            .number());
        if (!member.isPresent()) {
            log.warn("Profile with number {} not found", fee.member()
                .number());
        }

        feeType = feeTypeSpringRepository.findByNumber(fee.feeType()
            .number());
        if (!member.isPresent()) {
            log.warn("Profile with number {} not found", fee.member()
                .number());
        }

        if (fee.transaction()
            .isPresent()) {
            paid = true;
            transaction = transactionSpringRepository.findByIndex(fee.transaction()
                .get()
                .index());
            if (transaction.isEmpty()) {
                log.warn("Transaction with index {} not found", fee.transaction()
                    .get()
                    .index());
            } else {
                transaction.get()
                    .setDate(fee.transaction()
                        .get()
                        .date());
            }
        } else {
            paid = false;
            transaction = Optional.empty();
        }

        entity = new FeeEntity();
        entity.setMember(member.orElse(null));
        date = fee.month()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        entity.setMonth(date);
        entity.setPaid(paid);
        entity.setFeeType(feeType.orElse(null));
        entity.setTransaction(transaction.orElse(null));

        return entity;
    }

}
