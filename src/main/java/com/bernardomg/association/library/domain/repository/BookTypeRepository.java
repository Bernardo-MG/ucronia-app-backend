
package com.bernardomg.association.library.domain.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.BookType;

public interface BookTypeRepository {

    public boolean exists(final String name);

    public Iterable<BookType> findAll(final Pageable pageable);

    public BookType save(final BookType book);

}
