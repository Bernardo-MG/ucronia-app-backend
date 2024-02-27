
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.BookType;

public interface BookTypeService {

    public BookType createBookType(final BookType type);

    public void deleteBookType(final String name);

    public Iterable<BookType> getAllBookTypes(final Pageable pageable);

    public Optional<BookType> getOneBookType(final String name);

}
