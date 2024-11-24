
package com.bernardomg.association.library.lending.usecase.service;

import java.time.LocalDate;

import com.bernardomg.association.library.lending.domain.model.BookLending;

public interface BookLendingService {

    public BookLending lendBook(final long book, final long borrower, final LocalDate date);

    public BookLending returnBook(final long book, final long borrower, final LocalDate date);

}
