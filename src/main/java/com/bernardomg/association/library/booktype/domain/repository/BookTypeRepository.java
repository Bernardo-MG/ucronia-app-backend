
package com.bernardomg.association.library.booktype.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public interface BookTypeRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<BookType> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<BookType> findOne(final String name);

    public boolean hasRelationships(final String name);

    public BookType save(final BookType bookType);

}
