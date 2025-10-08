
package com.bernardomg.association.fee.domain.repository;

import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface FeeRepository {

    public void delete(final Long number, final YearMonth date);

    public boolean exists(final Long number, final YearMonth date);

    public boolean existsPaid(final Long number, final YearMonth date);

    public Page<Fee> findAll(final FeeQuery query, final Pagination pagination, final Sorting sorting);

    public Page<Fee> findAllForPerson(final Long number, final Pagination pagination, final Sorting sorting);

    public Collection<Fee> findAllInMonth(final YearMonth date);

    public Collection<Fee> findAllInYear(final Year year, final Sorting sorting);

    public Collection<Fee> findAllInYearForActiveMembers(final Year year, final Sorting sorting);

    public Collection<Fee> findAllInYearForInactiveMembers(final Year year, final Sorting sorting);

    public Optional<Fee> findOne(final Long number, final YearMonth date);

    public YearsRange findRange();

    public Collection<Fee> save(final Collection<Fee> fees);

    public Fee save(final Fee fee);

}
