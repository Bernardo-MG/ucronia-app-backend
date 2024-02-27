
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;

public interface BookService {

    public Book createBook(final Book book);

    public void deleteBook(final String isbn);

    public Iterable<Book> getAllBooks(final Pageable pageable);

    public Optional<Book> getOneBook(final String isbn);

}
