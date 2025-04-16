
package com.bernardomg.association.library.book.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.Book;

public interface BookRepository {

    public Optional<Book> findOne(final long number);

}
