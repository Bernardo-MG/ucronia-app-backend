
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.BookType;

public interface BookTypeService {

    public BookType create(final BookType type);

    public void delete(final String name);

    public Iterable<BookType> getAll(final Pageable pageable);

    public Optional<BookType> getOne(final String name);

}
