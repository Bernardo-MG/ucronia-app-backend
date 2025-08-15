
package com.bernardomg.association.library.lending.usecase.service;

import java.time.Instant;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface BookLendingService {

    public Iterable<BookLending> getAll(final Pagination pagination, final Sorting sorting);

    public BookLending lendBook(final long bookNumber, final long borrower, final Instant date);

    public BookLending returnBook(final long bookNumber, final long borrower, final Instant date);

}
