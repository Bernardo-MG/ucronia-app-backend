
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Publisher;

public interface PublisherRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<Publisher> getAll(final Pageable pageable);

    public Optional<Publisher> getOne(final String name);

    public Publisher save(final Publisher publisher);

}
