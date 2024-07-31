
package com.bernardomg.association.library.publisher.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public interface PublisherRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<Publisher> findAll(final Pageable pageable);

    public Optional<Publisher> findOne(final String name);

    public boolean hasRelationships(final String name);

    public Publisher save(final Publisher publisher);

}
