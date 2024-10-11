
package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.booktype.domain.model.BookType;

public interface BookTypeService {

    public BookType create(final BookType type);

    public void delete(final Long number);

    public Iterable<BookType> getAll(final Pageable pageable);

    public Optional<BookType> getOne(final Long number);

    public BookType update(final BookType type);

}
