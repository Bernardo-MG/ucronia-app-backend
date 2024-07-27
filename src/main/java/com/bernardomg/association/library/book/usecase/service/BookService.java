
package com.bernardomg.association.library.book.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.book.domain.model.Book;

public interface BookService {

    public Book create(final Book book);

    public void delete(final long number);

    public Iterable<Book> getAll(final Pageable pageable);

    public Optional<Book> getOne(final long number);

    public Book update(final long number, final Book book);

}
