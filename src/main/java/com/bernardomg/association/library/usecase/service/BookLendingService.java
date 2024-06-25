
package com.bernardomg.association.library.usecase.service;

public interface BookLendingService {

    public void lendBook(final long index, final long person);

    public void returnBook(final long index, final long person);

}
