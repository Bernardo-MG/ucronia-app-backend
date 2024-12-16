
package com.bernardomg.association.library.book.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookService {

    public Book create(final Book book);

    public void delete(final long number);

    public Iterable<Book> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<Book> getOne(final long number);

    public Book update(final long number, final Book book);

}
