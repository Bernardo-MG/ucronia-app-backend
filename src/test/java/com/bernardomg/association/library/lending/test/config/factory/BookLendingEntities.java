
package com.bernardomg.association.library.lending.test.config.factory;

import com.bernardomg.association.library.book.test.config.factory.BookConstants;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;

public final class BookLendingEntities {

    public static final BookLendingEntity lent() {
        return BookLendingEntity.builder()
            .withBookId(1L)
            .withPersonId(1L)
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLendingEntity returned() {
        return BookLendingEntity.builder()
            .withBookId(1L)
            .withPersonId(1L)
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

}
