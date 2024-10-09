
package com.bernardomg.association.library.booktype.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public interface BookTypeRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByName(final String name);

    public Iterable<BookType> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<BookType> findOne(final Long number);

    public boolean hasRelationships(final Long number);

    public BookType save(final BookType bookType);

}
