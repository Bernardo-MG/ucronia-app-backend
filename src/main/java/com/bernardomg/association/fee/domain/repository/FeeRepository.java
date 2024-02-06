
package com.bernardomg.association.fee.domain.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.transaction.domain.model.Transaction;

public interface FeeRepository {

    public void delete(final Long memberNumber, final YearMonth date);

    public boolean exists(final Long memberNumber, final YearMonth date);

    public boolean existsPaid(final Long memberNumber, final YearMonth date);

    public Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable);

    public Collection<Fee> findAll(final Long memberNumber, final Collection<YearMonth> feeDates);

    public Collection<Fee> findAllForActiveMembers(final int year, final Sort sort);

    public Collection<Fee> findAllForInactiveMembers(final int year, final Sort sort);

    public Collection<Fee> findAllForPreviousMonth();

    public Collection<Fee> findAllInYear(final int year, final Sort sort);

    public Optional<Fee> findOne(final Long memberNumber, final YearMonth date);

    public FeeCalendarYearsRange findRange();

    public void pay(final Member member, final Collection<Fee> fees, final Transaction transaction);

    public Collection<Fee> save(final Collection<Fee> fees);

}
