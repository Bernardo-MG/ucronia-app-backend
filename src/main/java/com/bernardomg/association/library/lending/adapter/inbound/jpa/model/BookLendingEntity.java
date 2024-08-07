
package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "BookLending")
@IdClass(BookLendingId.class)
@Table(schema = "inventory", name = "book_lendings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
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

}
