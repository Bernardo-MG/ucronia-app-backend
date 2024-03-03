
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookService {

    public Book create(final Book book);

    public void delete(final String isbn);

    public Iterable<Book> getAll(final Pageable pageable);

    public Optional<Book> getOne(final String isbn);

}
