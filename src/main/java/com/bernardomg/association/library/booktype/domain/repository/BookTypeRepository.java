
package com.bernardomg.association.library.booktype.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public interface BookTypeRepository {

    public long findNextNumber();

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<BookType> findAll(final Pageable pageable);

    public Optional<BookType> findOne(final String name);

    public boolean hasRelationships(final String name);

    public BookType save(final BookType bookType);

}
