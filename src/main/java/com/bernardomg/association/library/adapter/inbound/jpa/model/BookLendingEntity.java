
package com.bernardomg.association.library.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.YearMonth;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "BookLending")
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

    @Column(name = "book_id", nullable = false)
    private Long              bookId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "lending_date", nullable = false)
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth         lendingDate;

    @Column(name = "person_id", nullable = false)
    private Long              personId;

    @Column(name = "return_date", nullable = false)
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth         returnDate;

}
