
package com.bernardomg.association.library.book.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface GameBookService {

    public GameBook create(final GameBook book);

    public void delete(final long number);

    public Iterable<GameBook> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<GameBook> getOne(final long number);

    public GameBook update(final long number, final GameBook book);

}
