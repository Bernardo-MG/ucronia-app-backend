
package com.bernardomg.association.library.book.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.book.domain.model.Book;

public interface BookRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByIsbn(final String isbn);

    public boolean existsByIsbnForAnother(final long number, final String isbn);

    public Iterable<Book> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Book> findOne(final long number);

    public Book save(final Book book);

}
