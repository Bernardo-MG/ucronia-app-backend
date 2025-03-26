
package com.bernardomg.association.library.lending.domain.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookLendingRepository {

    public Iterable<BookLending> findAll(final Pagination pagination, final Sorting sorting);

    public Optional<BookLending> findLent(final long bookNumber);

    public Optional<BookLending> findOne(final long bookNumber, final long personNumber);

    public Optional<BookLending> findReturned(final long bookNumber);

    public Optional<BookLending> findReturned(final long bookNumber, final long personNumber,
            final LocalDate lendingDate);

    public BookLending save(final BookLending lending);

}
