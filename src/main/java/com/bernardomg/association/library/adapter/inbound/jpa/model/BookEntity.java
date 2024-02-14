
package com.bernardomg.association.library.adapter.inbound.jpa.model;

import java.io.Serializable;

import com.bernardomg.association.library.domain.model.System;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Book")
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "isbn", nullable = false)
    private String            isbn;

    @Column(name = "system", nullable = false)
    private System            system;

    @Column(name = "title", nullable = false)
    private String            title;

}
