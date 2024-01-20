
package com.bernardomg.association.fee.domain.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.infra.jpa.model.FeeEntity;

public interface FeeRepository {

    public void delete(final Long memberNumber, final YearMonth date);

    public boolean exists(final Long memberNumber, final YearMonth date);

    public Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable);

    public Optional<Fee> findOne(final Long memberNumber, final YearMonth date);

    public Collection<FeeEntity> save(final Long memberNumber, final Collection<YearMonth> feeDates);

}
