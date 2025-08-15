
package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;

public class BookLendingId implements Serializable {

    private static final long serialVersionUID = 6408736964641525631L;

    private Long              bookId;

    private Instant           lendingDate;

    private Long              personId;

    public Long getBookId() {
        return bookId;
    }

    public Instant getLendingDate() {
        return lendingDate;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setBookId(final Long bookId) {
        this.bookId = bookId;
    }

    public void setLendingDate(final Instant lendingDate) {
        this.lendingDate = lendingDate;
    }

    public void setPersonId(final Long personId) {
        this.personId = personId;
    }

}
