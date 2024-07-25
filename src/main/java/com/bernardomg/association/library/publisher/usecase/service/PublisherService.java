
package com.bernardomg.association.library.publisher.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public interface PublisherService {

    public Publisher create(final Publisher author);

    public void delete(final String name);

    public Iterable<Publisher> getAll(final Pageable pageable);

    public Optional<Publisher> getOne(final String name);

}
