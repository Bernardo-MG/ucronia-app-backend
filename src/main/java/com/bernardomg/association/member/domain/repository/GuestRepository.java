
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Guest;

public interface GuestRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public Iterable<Guest> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Guest> findOne(final Long number);

    public Guest save(final Guest guest);

}
