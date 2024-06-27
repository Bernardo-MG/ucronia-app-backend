
package com.bernardomg.association.library.domain.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookLending;

public interface BookLendingRepository {

    public Optional<BookLending> findOne(final long book, final long person);

    public Optional<BookLending> returnAt(final long book, final long person, final LocalDate date);

    public BookLending save(final BookLending lending);

}
