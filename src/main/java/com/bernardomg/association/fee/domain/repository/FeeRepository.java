
package com.bernardomg.association.fee.domain.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.member.domain.model.Member;

public interface FeeRepository {

    public void delete(final Long memberNumber, final YearMonth date);

    public boolean exists(final Long memberNumber, final YearMonth date);

    public boolean existsPaid(final Long memberNumber, final YearMonth date);

    public Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable);

    public Collection<Fee> findAll(final Long memberNumber, final Collection<YearMonth> feeDates);

    public Optional<Fee> findOne(final Long memberNumber, final YearMonth date);

    public void pay(final Member member, final Collection<Fee> fees, final LocalDate payDate);

    public Collection<Fee> save(final Long memberNumber, final Collection<YearMonth> feeDates);

}
