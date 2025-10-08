
package com.bernardomg.association.library.book.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface FictionBookService {

    public FictionBook create(final FictionBook book);

    public FictionBook delete(final long number);

    public Page<FictionBook> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<FictionBook> getOne(final long number);

    public FictionBook update(final FictionBook book);

}
