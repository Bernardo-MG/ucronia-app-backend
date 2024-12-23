
package com.bernardomg.association.library.publisher.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface PublisherRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final Long number);

    public Iterable<Publisher> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<Publisher> findOne(final long number);

    public Publisher save(final Publisher publisher);

}
