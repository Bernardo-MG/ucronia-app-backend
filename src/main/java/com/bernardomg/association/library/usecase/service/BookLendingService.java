
package com.bernardomg.association.library.usecase.service;

import java.time.LocalDate;

public interface BookLendingService {

    public void lendBook(final long book, final long person, final LocalDate date);

    public void returnBook(final long book, final long person, final LocalDate date);

}
