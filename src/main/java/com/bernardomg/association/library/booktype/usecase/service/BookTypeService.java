
package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookTypeService {

    public BookType create(final BookType type);

    public void delete(final Long number);

    public Iterable<BookType> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<BookType> getOne(final Long number);

    public BookType update(final BookType type);

}
