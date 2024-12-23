
package com.bernardomg.association.library.book.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByIsbn(final String isbn);

    public boolean existsByIsbnForAnother(final long number, final String isbn);

    public Iterable<Book> findAll(final Pagination pagination, final Sorting sorting);

    public Collection<Book> findAll(final Sorting sorting);

    public long findNextNumber();

    public Optional<Book> findOne(final long number);

    public Book save(final Book book);

}
