
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingEntity;

public final class BookLendingEntities {

    public static final BookLendingEntity returned() {
        return BookLendingEntity.builder()
            .withBookId(1L)
            .withPersonId(1L)
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

}
