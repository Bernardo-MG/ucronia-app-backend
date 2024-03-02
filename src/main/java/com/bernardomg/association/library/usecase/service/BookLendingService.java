
package com.bernardomg.association.library.usecase.service;

public interface BookLendingService {

    public void lendBook(final String isbn, final long member);

    public void returnBook(final String isbn, final long member);

}
