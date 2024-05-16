
package com.bernardomg.association.fee.domain.repository;

import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.transaction.domain.model.Transaction;

public interface FeeRepository {

    public void delete(final Long number, final YearMonth date);

    public boolean exists(final Long number, final YearMonth date);

    public boolean existsPaid(final Long number, final YearMonth date);

    public Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable);

    public Collection<Fee> findAllForActiveMembers(final Year year, final Sort sort);

    public Collection<Fee> findAllForInactiveMembers(final Year year, final Sort sort);

    public Iterable<Fee> findAllForMember(final Long number, final Pageable pageable);

    public Collection<Fee> findAllForMemberInDates(final Long number, final Collection<YearMonth> feeDates);

    public Collection<Fee> findAllForPreviousMonth();

    public Collection<Fee> findAllInMonth(final YearMonth date);

    public Collection<Fee> findAllInYear(final Year year, final Sort sort);

    public Optional<Fee> findOne(final Long number, final YearMonth date);

    public FeeCalendarYearsRange findRange();

    public void pay(final Person person, final Collection<Fee> fees, final Transaction transaction);

    public Collection<Fee> save(final Collection<Fee> fees);

}
