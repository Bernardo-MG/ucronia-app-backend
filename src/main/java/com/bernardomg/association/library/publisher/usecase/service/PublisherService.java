
package com.bernardomg.association.library.publisher.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface PublisherService {

    public Publisher create(final Publisher publisher);

    public void delete(final long number);

    public Iterable<Publisher> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<Publisher> getOne(final long number);

    public Publisher update(final Publisher publisher);

}
