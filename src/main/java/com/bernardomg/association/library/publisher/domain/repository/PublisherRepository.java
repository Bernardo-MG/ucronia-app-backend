
package com.bernardomg.association.library.publisher.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public interface PublisherRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public Iterable<Publisher> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Publisher> findOne(final long number);

    public Publisher save(final Publisher publisher);

}
