
package com.bernardomg.association.library.usecase.service;

import java.time.YearMonth;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;

public final class DefaultBookLendingService implements BookLendingService {

    private final BookLendingRepository bookLendingRepository;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo) {
        super();

        bookLendingRepository = bookLendingRepo;
    }

    @Override
    public void lendBook(final String isbn, final long member) {
        final BookLending lending;
        final YearMonth   now;

        now = YearMonth.now();
        lending = BookLending.builder()
            .withIsbn(isbn)
            .withMember(member)
            .withLendingDate(now)
            .build();

        bookLendingRepository.create(lending);
    }

}
