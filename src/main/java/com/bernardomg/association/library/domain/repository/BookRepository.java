
package com.bernardomg.association.library.domain.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookRepository {

    public Iterable<Book> findAll(final Pageable pageable);

    public Book save(final Book book);

}
