
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookLending;

public interface BookLendingRepository {

    public Optional<BookLending> findOne(final long index, final long member);

    public BookLending save(final BookLending lending);

}
