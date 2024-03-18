
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookRepository {

    public void delete(final long index);

    public boolean exists(final long index);

    public boolean existsByIsbn(final String isbn);

    public long findNextIndex();

    public Iterable<Book> getAll(final Pageable pageable);

    public Optional<Book> getOne(final long index);

    public Book save(final Book book);

}
