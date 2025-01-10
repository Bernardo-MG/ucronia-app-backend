
package com.bernardomg.association.fee.domain.repository;

import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface FeeRepository {

    public void delete(final Long number, final YearMonth date);

    public boolean exists(final Long number, final YearMonth date);

    public boolean existsPaid(final Long number, final YearMonth date);

    public Iterable<Fee> findAll(final FeeQuery query, final Pagination pagination, final Sorting sorting);

    public Collection<Fee> findAllForActiveMembers(final Year year, final Sorting sorting);

    public Collection<Fee> findAllForInactiveMembers(final Year year, final Sorting sorting);

    public Iterable<Fee> findAllForPerson(final Long number, final Pagination pagination, final Sorting sorting);

    public Collection<Fee> findAllForPersonInDates(final Long number, final Collection<YearMonth> feeMonths);

    public Collection<Fee> findAllInMonth(final YearMonth date);

    public Collection<Fee> findAllInYear(final Year year, final Sorting sorting);

    public Optional<Fee> findOne(final Long number, final YearMonth date);

    public FeeCalendarYearsRange findRange();

    public Collection<Fee> pay(final Person person, final Collection<Fee> fees, final Transaction transaction);

    public Collection<Fee> save(final Collection<Fee> fees);

    public Fee save(final Fee fee);

}
