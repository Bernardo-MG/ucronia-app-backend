
package com.bernardomg.association.library.publisher.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public interface PublisherService {

    public Publisher create(final Publisher publisher);

    public void delete(final long number);

    public Iterable<Publisher> getAll(final Pageable pageable);

    public Optional<Publisher> getOne(final long number);

    public Publisher update(final Publisher publisher);

}
