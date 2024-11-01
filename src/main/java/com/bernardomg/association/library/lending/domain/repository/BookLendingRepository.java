
package com.bernardomg.association.library.lending.domain.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;

public interface BookLendingRepository {

    public Optional<BookLending> findLent(final long bookNumber);

    public Optional<BookLending> findOne(final long bookNumber, final long personNumber);

    public Optional<BookLending> findReturned(final long bookNumber);

    public Optional<BookLending> findReturned(final long bookNumber, final long personNumber,
            final LocalDate lendingDate);

    public BookLending save(final BookLending lending);

}
