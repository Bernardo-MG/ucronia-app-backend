
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByIsbn(final String isbn);

    public boolean existsByIsbnForAnother(final Long number, final String isbn);

    public long findNextNumber();

    public Iterable<Book> getAll(final Pageable pageable);

    public Optional<Book> getOne(final long number);

    public Book save(final Book book);

}
