
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.BookType;

public interface BookTypeRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<BookType> findAll(final Pageable pageable);

    public Optional<BookType> findOne(final String name);

    public boolean hasRelationships(final String name);

    public BookType save(final BookType bookType);

}
