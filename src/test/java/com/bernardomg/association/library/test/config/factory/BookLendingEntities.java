
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingEntity;

public final class BookLendingEntities {

    public static final BookLendingEntity lentNow() {
        return BookLendingEntity.builder()
            .withBookId(1L)
            .withPersonId(1L)
            .withLendingDate(LocalDate.now())
            .build();
    }

}
