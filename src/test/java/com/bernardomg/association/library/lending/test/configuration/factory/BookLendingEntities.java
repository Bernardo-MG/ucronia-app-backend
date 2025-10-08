
package com.bernardomg.association.library.lending.test.configuration.factory;

import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;

public final class BookLendingEntities {

    public static final BookLendingEntity lent() {
        final BookLendingEntity entity = new BookLendingEntity();
        entity.setBookId(1L);
        entity.setPersonId(1L);
        entity.setLendingDate(BookConstants.LENT_DATE);
        return entity;
    }

    public static final BookLendingEntity returned() {
        final BookLendingEntity entity = new BookLendingEntity();
        entity.setBookId(1L);
        entity.setPersonId(1L);
        entity.setLendingDate(BookConstants.LENT_DATE);
        entity.setReturnDate(BookConstants.RETURNED_DATE);
        return entity;
    }

    private BookLendingEntities() {
        super();
    }

}
