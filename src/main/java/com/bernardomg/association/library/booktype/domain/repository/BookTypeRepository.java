
package com.bernardomg.association.library.booktype.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookTypeRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final long number);

    public Page<BookType> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<BookType> findOne(final long number);

    public BookType save(final BookType bookType);

}
