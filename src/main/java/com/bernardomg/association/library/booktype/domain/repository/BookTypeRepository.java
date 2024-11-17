
package com.bernardomg.association.library.booktype.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public interface BookTypeRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final long number);

    public Iterable<BookType> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<BookType> findOne(final long number);

    public BookType save(final BookType bookType);

}
