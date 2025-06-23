
package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "BookLending")
@IdClass(BookLendingId.class)
@Table(schema = "inventory", name = "book_lendings")
public class BookLendingEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @Column(name = "book_id", nullable = false)
    private Long              bookId;

    @Id
    @Column(name = "lending_date", nullable = false)
    private LocalDate         lendingDate;

    @Id
    @Column(name = "person_id", nullable = false)
    private Long              personId;

    @Column(name = "return_date", nullable = false)
    private LocalDate         returnDate;

    public Long getBookId() {
        return bookId;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public Long getPersonId() {
        return personId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setBookId(final Long bookId) {
        this.bookId = bookId;
    }

    public void setLendingDate(final LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public void setPersonId(final Long personId) {
        this.personId = personId;
    }

    public void setReturnDate(final LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BookLendingEntity [bookId=" + bookId + ", lendingDate=" + lendingDate + ", personId=" + personId
                + ", returnDate=" + returnDate + "]";
    }

}
