
package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BookLendingId implements Serializable {

    private static final long serialVersionUID = 6408736964641525631L;

    private Long              bookId;

    private LocalDate         lendingDate;

    private Long              personId;

}
