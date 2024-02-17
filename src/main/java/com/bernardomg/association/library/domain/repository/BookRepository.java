
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookRepository {

    public Iterable<Book> findAll(final Pageable pageable);

    public Optional<Book> findOne(final String isbn);

    public Book save(final Book book);

}
