
package com.bernardomg.association.library.book.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface FictionBookRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByIsbn(final String isbn);

    public boolean existsByIsbnForAnother(final long number, final String isbn);

    public Page<FictionBook> findAll(final Pagination pagination, final Sorting sorting);

    public Collection<FictionBook> findAll(final Sorting sorting);

    public long findNextNumber();

    public Optional<FictionBook> findOne(final long number);

    public FictionBook save(final FictionBook book);

}
